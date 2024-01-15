package io.ganguo.tools.screen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 
 * 生成屏幕适配文件 使用方法： 配置各个ScreenType的公式后，直接运行本文件, 输出目录:本项目下screen_file
 * 
 * @author Wilson
 *
 */
public class ScreenGen {
	private final static int MAX_DP = 750;
	private final static int MAX_NDP = 360;
	private final static int MAX_FONT = 80;

	private enum ScreenType {
		Default, W720DP, W960DP, W1024DP, W1280DP, HDPI, XHDPI, XXHDPI;

		private int genDP(int i) {
			switch (this) {
			case Default:// 默认1:1
				return i;
			case W720DP:
				return (int) (i * 0.7) + 1;
			case W960DP:
				return (int) (i * 0.8) + 1;
			case W1024DP:
				return i;
			case W1280DP:
				return i;
			case HDPI:
				return i - 1;
			case XHDPI:
				return i;
			case XXHDPI:
				return i + 1;
			}
			return i;
		}

		private int genSP(int i) {
			switch (this) {
			case Default:// 默认1:1
				return i;
			case W720DP:
				return (int) (i * 0.7) + 1;
			case W960DP:
				return (int) (i * 0.8) + 1;
			case W1024DP:
				return i;
			case W1280DP:
				return i;
			case HDPI:
				return i - 1;
			case XHDPI:
				return i;
			case XXHDPI:
				return i + 1;
			}
			return i;
		}
	}

	private static void checkStorePath() {
		File directory = new File(getFilePath());
		if (!directory.exists()) {
			directory.mkdir();
		}
	}

	private static String getFilePath() {
		String path = "";
		File directory = new File("");// 设定为当前文件夹
		try {
			return directory.getAbsolutePath() + "/screen_file";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}

	private static void outDP(ScreenType screenType) {
		File dpFile = new File(getFilePath() + "/" + screenType.name() + "/dimen_dp_" + screenType.name() + ".xml");
		String header = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + "<resources>\n";
		String screenSize = "<string name=\"screenSize\">" + screenType.name() + "</string>\n";
		String footer = "\n</resources>";
		// gen dp
		try {
			FileOutputStream fop = new FileOutputStream(dpFile);
			dpFile.createNewFile();
			fop.write(header.getBytes());
			fop.write(screenSize.getBytes());
			for (int i = 1; i <= MAX_DP; i++) {
				int dpVal = screenType.genDP(i);
				dpVal = dpVal == 0 ? 1 : dpVal;
				fop.write(("<dimen name=\"dp_" + i + "\">" + dpVal + "dp</dimen>\n").getBytes());
			}
			for (int i = 1; i <= MAX_NDP; i++) {
				int dpVal = screenType.genDP(i);
				dpVal = dpVal == 0 ? 1 : dpVal;
				fop.write(("<dimen name=\"dp_n" + i + "\">" + (-dpVal) + "dp</dimen>\n").getBytes());
			}
			fop.write(footer.getBytes());
			fop.flush();
			fop.close();
			System.out.println("Done DP: " + screenType.name());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void outSP(ScreenType screenType) {
		File fontFile = new File(getFilePath() + "/" + screenType.name() + "/dimen_font_" + screenType.name() + ".xml");
		String header = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + "<resources>\n";
		String footer = "\n</resources>";
		// gen font
		try {
			FileOutputStream fop = new FileOutputStream(fontFile);
			fontFile.createNewFile();
			fop.write(header.getBytes());
			for (int i = 1; i <= MAX_FONT; i++) {
				int spVal = screenType.genSP(i);
				spVal = spVal == 0 ? 1 : spVal;
				fop.write(("<dimen name=\"font_" + i + "\">" + spVal + "sp</dimen>\n").getBytes());
			}
			fop.write(footer.getBytes());
			fop.flush();
			fop.close();
			System.out.println("Done SP: " + screenType.name());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		checkStorePath();
		for (ScreenType screenType : ScreenType.values()) {
			File directory = new File(getFilePath() + "/" + screenType.name());
			if (!directory.exists()) {
				directory.mkdir();
			}
			outDP(screenType);
			outSP(screenType);
		}
	}
}
