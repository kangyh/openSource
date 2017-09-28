package info.xiaohei.www.hdfs;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import info.xiaohei.www.HdfsUtil;

public class TestHdfs {
	private String path="/user/hadoop/kangyh/kpi/";
	
	@Before
	public void sysfile(){
		System.setProperty("hadoop.home.dir", "C:\\hadoop-common-2.2.0");
	}
	
	@After
	public void show(){
		try {
			HdfsUtil.ls(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	@Test
//	public void fileUpload(){
//		try {
//			String file="access.20120104.log";
//			String localpath="E:\\file\\";
//			
//			String local=localpath+file;
//			String remote=path+file;
//			HdfsUtil.copyFile(local, remote);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
//	@Test
//	public void viewFile(){
//		try {
//			String file="access.20120104.log";
//			String remoteFile=path+file;
//			HdfsUtil.cat(remoteFile);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	@Test
	public void delFile(){
		try {
			String file=path+"smallFile.txt";
			HdfsUtil.rmr(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	@Test
//	public void createFile(){
//		String file=path+"smallFile.txt";
//		try {
//			HdfsUtil.createFile(file, "12345");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}
