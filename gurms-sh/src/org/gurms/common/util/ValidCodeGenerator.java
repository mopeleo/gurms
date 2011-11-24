package org.gurms.common.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

public class ValidCodeGenerator {
	//大写I与小写L区别不出来，导致输入错误，屏蔽掉
	private final static char[] CODES = {'A','B','C','D','E','F','G','H',
			'J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X',
			'Y','Z','a','b','c','d','e','f','g','h','j','k','m','n','o',
			'p','q','r','s','t','u','v','w','x','y','z'};

	private ValidCodeGenerator(){}
	
	public static void generate(OutputStream stream, String code, int width,
			int height) throws IOException {

		BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = buffImg.createGraphics();
		Random random = new Random();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		Font font = new Font(Font.DIALOG, Font.PLAIN | Font.BOLD, 25);
		g.setFont(font);
		for (int i = 0; i < 200; i++) {
			g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			g.drawLine(x, y, x, y);
		}
		int offX = 10;
		int offY = 20;
		for (int i = 0; i < code.length(); i++) {
			g.setColor(new Color(random.nextInt(128), random.nextInt(128), random.nextInt(128)));
			g.drawString(code.substring(i, i + 1), offX, offY);
			offX += 23;
		}
		ImageIO.write(buffImg, "jpeg", stream);
		g.dispose();
		buffImg.flush();
	}

	public static String generate(OutputStream out) throws IOException {
		int n = 4;
		int width = 100;
		int height = 25;
		char[] codes = new char[n];
		Random random = new Random();
		for (int i = 0; i < n; i++) {
			codes[i] = CODES[random.nextInt(CODES.length)];
		}
		String code = new String(codes);
		generate(out, code, width, height);
		return code;
	}
}