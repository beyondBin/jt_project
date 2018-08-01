package com.jt.manage.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jt.common.vo.PicUploadResult;
import com.jt.manage.service.FileService;
@Service
public class FileServiceImpl implements FileService {

	private String fileDir = "F:/jt-upload/"; //文件存储路径
	private String urlDir = "http://image.jt.com/"; //域名
	/**
	 * 1.判断是否为图片	jpg、png、gif...
	 * 2.判断是否为恶意程序
	 * 3.为了图片检索方便,采用分文件存储
	 * 	3.1	UUID(很少用)
	 * 	3.2	时间	yyyy/MM/dd
	 * 4.用户上传的图片可能重名  UUID+随机数
	 * 5.实现文件上传
	 */
	
	@Override
	public PicUploadResult upload(MultipartFile uploadFile) {
		PicUploadResult result = new PicUploadResult();
		//1.判断是否为图片
			//1.1获取图片名称
			String fileName = uploadFile.getOriginalFilename();
			//1.2判断是否为图片类型
			if(!fileName.matches("^.*(jpg|png|gif)$")){
				//表示不是图片
				result.setError(1);
				return result;
			}
		try {
			//2.判断程序是否为恶意代码
			BufferedImage bufferedImage = ImageIO.read(uploadFile.getInputStream());
				//2.1获取宽度和高度(恶意程序无宽高)
				int width = bufferedImage.getWidth();
				int height = bufferedImage.getHeight();
				if(0 == width || 0 == height){
				 	result.setError(1);
					return result;
				}
			//3.准备分文件存储路径
			String dateDir = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
			//4.准备文件保存的目录 ( F:/jt-upload/2018/07/03 )
			String filePath = fileDir + dateDir;
			File imageFile = new File(filePath);
				//4.1判断文件夹是否存在,不存在则创建
				if(!imageFile.exists()){
				  	imageFile.mkdirs();
				}
			//5.生成全新文件名称
				//5.1
				String fileType = fileName.substring(fileName.lastIndexOf("."));
				//5.2获取随机数并去除"-",再生成随机数(0-998)
				String uuid = UUID.randomUUID().toString().replace("-", "");
				int random = new Random().nextInt(999);
				//5.3拼接文件名称
				String realFileName = uuid + random +fileType;
				//5.4拼接文件全路径( F:/jt-upload/2018/07/03/XXXX.jpg )
				String localFilePath = filePath+"/"+realFileName;
			//6.上传文件
			uploadFile.transferTo(new File(localFilePath));
			//7.为返回值封装数据
			result.setHeight(height+"");
			result.setWidth(width+"");
			//进行回显路径的拼接( http://image.jt.com/2018/07/03/XXXX.jpg )
			String urlPath = urlDir+dateDir+"/"+realFileName;
			result.setUrl(urlPath);
			
		} catch (IOException e) {
			e.printStackTrace();
			result.setError(1);
			return result;
		}
		return result;
	}
}
