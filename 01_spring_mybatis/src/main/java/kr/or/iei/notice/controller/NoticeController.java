package kr.or.iei.notice.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.iei.notice.model.dto.Notice;
import kr.or.iei.notice.model.dto.NoticeFile;
import kr.or.iei.notice.model.dto.NoticePageData;
import kr.or.iei.notice.model.service.NoticeService;

@Controller
@RequestMapping("/notice")
public class NoticeController {
	
	@Autowired
	private NoticeService service;
	
	//application.properties 사용자 정의 속성값 읽어오기
	@Value("${file.uploadPath}")
	private String uploadPath;
	
	
	@GetMapping("/getList")
	public String getList(int reqPage, Model model, String select, String keyword) {		
		NoticePageData pd = service.selectNoticeList(reqPage, select, keyword);
		
		model.addAttribute("noticeList", pd.getList());
		model.addAttribute("pageNavi", pd.getPageNavi());
		
		//조건 입력 후, 검색하면 다시 게시글 목록 페이지가 보이는데 화면에 조건식이 유지될 수 있도록.
		model.addAttribute("select", select);
		model.addAttribute("keyword", keyword);
		
		return "notice/list";
	}
	
	@GetMapping("/writeFrm")
	public String writeFrm() {		
		return "notice/write";
	}
	
	@PostMapping("/write")
	public String wirte(MultipartFile [] files, Notice notice, HttpServletRequest request) {
				
		//서비스에 전달할 파일 객체 리스트
		ArrayList<NoticeFile> fileList = new ArrayList<NoticeFile>();
		
		for(int i=0; i<files.length; i++) {
			MultipartFile file = files[i]; //여러개 파일 중 1개
			
			if(!file.isEmpty()) {
				String originFileName = file.getOriginalFilename(); //업로드한 실제 파일명 Ex) test.txt
				String fileName = originFileName.substring(0, originFileName.lastIndexOf(".")); // test
				String extension = originFileName.substring(originFileName.lastIndexOf(".")); // .text
				
				String toDay = new SimpleDateFormat("yyyyMMdd").format(new Date()); //20250613
				int ranNum = new Random().nextInt(10000)+1; // 1~10000 사이 숫자 중, 랜덤값
				String serverFileName = fileName + "_" + toDay + "_" +ranNum + extension; //test_20250613_랜덤숫자.txt
				
				//src/main/resources/static/upload/test_20250613_랜덤숫자.txt
				String savePath = uploadPath + serverFileName;
				
				BufferedOutputStream bos = null;
				
				try {
					//파일 정보 바이트 배열로
					byte [] bytes = file.getBytes();
					//보조 스트림 <> 주 스트림 연결
					bos = new BufferedOutputStream(new FileOutputStream(new File(savePath)));
					//파일 내보내기
					bos.write(bytes);
					
					//DB에 저장할 파일 정보 생성하여 리스트에 추가
					NoticeFile noticeFile = new NoticeFile();
				    noticeFile.setFileName(originFileName);
				    noticeFile.setFilePath(serverFileName);
				    fileList.add(noticeFile);
					
				}catch(IOException e) {
					e.printStackTrace();
				}finally {
					try {
						bos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}				
			}
		}
		
		//DB에 게시글 및 파일 정보 등록
		int result = service.insertNotice(notice, fileList);		
		
		return "redirect:/notice/getList?reqPage=1";
	}
	
	@GetMapping("/getInfo")
	public String getInfo(String noticeNo, Model model) {
		Notice notice = service.selectOneNotice(noticeNo);
		model.addAttribute("notice", notice);
		return "notice/detailView";		
		
	}
	
	@GetMapping("/fileDown")
	public void noticeFileDown(HttpServletResponse response,
							   String fileName,
							   String filePath) {
		//서버에서 파일을 읽어들일 보조스팀
		BufferedInputStream bis = null;
		//파일 내보낼 보조스트림
		BufferedOutputStream bos = null;
		
		try {
			//파일 읽어들일 주 스트림
			FileInputStream fis = new FileInputStream(uploadPath + filePath);
			//보조스트림과 연결
			bis = new BufferedInputStream(fis);
			
			//파일 내보낼 주 스트림
			ServletOutputStream sos = response.getOutputStream();
			//보조스트림과 연결
			bos = new BufferedOutputStream(sos);
			
			//파일 다운로드 명칭
			String downLoadName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			
			//파일 다운로드 명칭 response 헤더에 설정
			response.setHeader("Content-Disposition",  "attachment; filename=" + downLoadName);
			
			//파일 읽어들이고 내보내기
			while(true) {
				int read = bis.read(); //읽어들일게 없으면 -1을 반환
				if(read == -1) {
					break;
				}else {
					bos.write(read);
				}
			}
		
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		} finally {
			try {
				bos.close();
				bis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}		
	}
	
	
	@GetMapping("/delete")
	public String delete(String noticeNo) {
		
		//DB에서 게시글 정보 삭제하고, 서버에서 파일 정보 삭제를 위해 게시글에 대한 파일 목록 조회
		ArrayList<NoticeFile> delFileList = service.deleteNotice(noticeNo);
		
		if(delFileList != null) {
			for(int i=0; i<delFileList.size(); i++) {
				NoticeFile delFile = delFileList.get(i);
				
				File file = new File(uploadPath + delFile.getFilePath());
				
				if(file.exists()) {
					file.delete();
				}
			}
		}
		
		return "redirect:/notice/getList?reqPage=1";
	}
	
	@GetMapping("/updateFrm")
	public String updateFrm(String noticeNo, Model model) {
		
		Notice notice = service.selectOneNotice(noticeNo);
		model.addAttribute("notice", notice);
		
		return "notice/update";
	}
	
	@PostMapping("/update")
	public String update(Notice notice, MultipartFile [] files) {
		
		/*
		 * files가 null인 경우.
		 * - 게시글을 처음 작성했을 때, 파일 업로드를 하였고 수정 시에 제목 및 내용만 수정한 경우
		 * 
		 * files가 null이 아닌 경우.
		 * - 게시글을 처음 작성했을 때, 파일 업로드를 하였고 수정시에 재업로드 버튼을 누른 경우
		 * - 게시글을 처음 작성했을 때, 파일 업로드를 하지 않은 경우
		 */
		boolean fileIsNull = files == null;
		
		ArrayList<NoticeFile> fileList = new ArrayList<NoticeFile>();
		
		//files가 null이 아닐 때
		if(!fileIsNull) {			
			
			for(int i=0; i<files.length; i++) {
				MultipartFile file = files[i]; //여러개 파일 중 1개
				
				if(!file.isEmpty()) {
					String originFileName = file.getOriginalFilename(); //업로드한 실제 파일명 Ex) test.txt
					String fileName = originFileName.substring(0, originFileName.lastIndexOf(".")); // test
					String extension = originFileName.substring(originFileName.lastIndexOf(".")); // .text
					
					String toDay = new SimpleDateFormat("yyyyMMdd").format(new Date()); //20250613
					int ranNum = new Random().nextInt(10000)+1; // 1~10000 사이 숫자 중, 랜덤값
					String serverFileName = fileName + "_" + toDay + "_" +ranNum + extension; //test_20250613_랜덤숫자.txt
					
					//src/main/resources/static/upload/test_20250613_랜덤숫자.txt
					String savePath = uploadPath + serverFileName;
					
					BufferedOutputStream bos = null;
					
					try {
						//파일 정보 바이트 배열로
						byte [] bytes = file.getBytes();
						//보조 스트림 <> 주 스트림 연결
						bos = new BufferedOutputStream(new FileOutputStream(new File(savePath)));
						//파일 내보내기
						bos.write(bytes);
						
						//DB에 저장할 파일 정보 생성하여 리스트에 추가
						NoticeFile noticeFile = new NoticeFile();
					    noticeFile.setFileName(originFileName);
					    noticeFile.setFilePath(serverFileName);
					    noticeFile.setNoticeNo(notice.getNoticeNo());
					    fileList.add(noticeFile);
						
					}catch(IOException e) {
						e.printStackTrace();
					}finally {
						try {
							bos.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}				
				}
			}			
		}
		
		//DB 작업 후, 서버에서 기존 파일 삭제를 위해 delFileList를 반환받음.
		ArrayList<NoticeFile> delFileList = service.updateNotice(notice, fileList, fileIsNull);
		
		/* 단순히, delFileList가 null이 아닐 때 서버에서 파일을 삭제하는 것이 아니라,
		 * fileIsNull이 false일 때(재업로드를 했거나, 게시글 첫 작성 시 파일 업로드를 하지 않은 경우)
		 * 
		 */
		if(delFileList != null && !fileIsNull) {
			for(int i=0; i<delFileList.size(); i++) {
				NoticeFile delFile = delFileList.get(i);
				
				File file = new File(uploadPath + delFile.getFilePath());
				
				if(file.exists()) {
					file.delete();
				}
			}
		}
		
		return "redirect:/notice/getList?reqPage=1";				
	}
	
}
