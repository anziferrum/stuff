import java.io.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.*;

public class ImgWriter {
	public static void main(String args[]) throws IOException {
		if (args.length < 3) {
			System.out.println("Usage: java ImgWriter <in_filename> <out_filename> <scaling_factor> [<divider> <contrast_color_hex>]");
			System.exit(0);
		}

		BufferedImage image = ImageIO.read(new File(args[0]));

		Color contrast = new Color(Integer.valueOf(args.length == 5 ? args[4] : "0000FF", 16) - 16777216);

		int divider = args.length == 4 ? Integer.valueOf(args[3]) : 1;
		int scaleFactor = Integer.valueOf(args[2]);
		int sfPlus = scaleFactor + 1;

		int dividedWidth = image.getWidth() / divider;
		int dividedHeight = image.getHeight() / divider;
		
		int outWidth = dividedWidth * (sfPlus) + 1;
		int outHeight = dividedHeight * (sfPlus) + 1;

		BufferedImage bi = new BufferedImage(outWidth, outHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D ig2 = bi.createGraphics();

		for (int y = 0; y < dividedHeight; y++) {
			ig2.setColor(contrast);
			ig2.drawLine(0, y * (sfPlus), outWidth - 1, y * (sfPlus));

			for (int x = 0; x < dividedWidth; x++) {
				ig2.setColor(contrast);
				ig2.drawLine(x * (sfPlus), y * (sfPlus) + 1, x * (sfPlus), y * (sfPlus) + scaleFactor);

				ig2.setColor(new Color(image.getRGB(x * divider, y * divider)));
				ig2.fillRect(x * (sfPlus) + 1, y * (sfPlus) + 1, scaleFactor, scaleFactor);
			}

			ig2.setColor(contrast);
			ig2.drawLine(outWidth - 1, y * (sfPlus) + 1, outWidth - 1, y * (sfPlus) + scaleFactor);
		}

		ig2.setColor(contrast);
		ig2.drawLine(0, outHeight - 1, outWidth - 1, outHeight - 1);

		ImageIO.write(bi, "PNG", new File(args[1]));
	}

}