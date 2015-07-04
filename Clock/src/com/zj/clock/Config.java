package com.zj.clock;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Config {

	private static String filepath = ClockActivity.filePath + "/config.ini";
	
	public static void create(){
		File file = new File(filepath);
		boolean isExist = file.exists();
		if(!isExist){
			try {
				FileWriter fw = new FileWriter(filepath);
				BufferedWriter bw = new BufferedWriter(fw);
				
				bw.write("金刚=001");
				bw.newLine();
				bw.write("雾岛=002");

				bw.close();
				fw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static List<String> read(){
		List<String> lists = new ArrayList<String>();
		try {
			FileReader fr = new FileReader(filepath);
			BufferedReader br = new BufferedReader(fr);
			String data;
			while((data=br.readLine())!=null){
				lists.add(data);
			}
			br.close();
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lists;
	}
	
}
