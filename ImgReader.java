import java.io.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.*;

public class ImgReader {
	public static void main(String args[]) throws IOException {
		if (args.length == 0) {
			System.out.println("Usage: java ImgReader <filename> [<coords_array_size> <color_value>]");
			System.exit(0);
		}

		BufferedImage image = ImageIO.read(new File(args[0]));

		int width = image.getWidth();
		int height = image.getHeight();

		Map<Integer, Integer> colors = new HashMap<>();

		boolean showCoords = args.length > 1;

		int[] xCoords = null;
		int[] yCoords = null;
		int color = 0;
		int counter = 0;
		int size = 0;

		if (showCoords) {
			size = Integer.valueOf(args[1]);
			color = Integer.valueOf(args[2]);
			xCoords = new int[size];
			yCoords = new int[size];
		}

		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int clr = image.getRGB(x, y);
				int current = colors.getOrDefault(clr, 0);
				colors.put(clr, current + 1);

				if (showCoords) {
					if (clr == color) {
						xCoords[counter] = x;
						yCoords[counter] = y;
						counter++;
					}
				}
			}
		}
		
		int i = 0;

		for (Map.Entry<Integer, Integer> e : colors.entrySet()) {
			int clr = e.getKey();
			int r = (clr & 0x00ff0000) >> 16;
			int g = (clr & 0x0000ff00) >> 8;
			int b = clr & 0x000000ff;
			i++;

			System.out.println(i + ": [" + r + ", " + g + ", " + b + "]: " + e.getValue() + " (" + clr + ")");
		}

		if (showCoords) {
			System.out.println("Coords:");

			for (i = 0; i < size; i++) {
				System.out.println(xCoords[i] + ", " + yCoords[i]);
			}
		}
	}

}