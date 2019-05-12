import java.io.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.*;

public class ImgWriter {
	public static void main(String args[]) throws IOException {
		if (args.length < 3) {
			System.out.println("Usage: java ImgWriter <in_filename> <out_filename> <scaling_factor> [<border_width> <divider> <contrast_color_hex>]");
			System.exit(0);
		}

		BufferedImage image = ImageIO.read(new File(args[0]));

		Color contrast = new Color(Integer.valueOf(args.length == 6 ? args[5] : "0000FF", 16) - 16777216);

		int divider = args.length >= 5 ? Integer.valueOf(args[4]) : 1;
		int scaleFactor = Integer.valueOf(args[2]);
		int borderWidth = args.length >= 4 ? Integer.valueOf(args[3]) : 0;
		int sfPlus = scaleFactor + borderWidth;

		int dividedWidth = image.getWidth() / divider;
		int dividedHeight = image.getHeight() / divider;
		
		int outWidth = dividedWidth * (sfPlus) + borderWidth;
		int outHeight = dividedHeight * (sfPlus) + borderWidth;

		BufferedImage bi = new BufferedImage(outWidth, outHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D ig2 = bi.createGraphics();

		for (int y = 0; y < dividedHeight; y++) {
			ig2.setColor(contrast);
			ig2.fillRect(0, y * sfPlus, outWidth, borderWidth);

			for (int x = 0; x < dividedWidth; x++) {
				ig2.setColor(contrast);
				ig2.fillRect(x * sfPlus, y * sfPlus + borderWidth, borderWidth, scaleFactor);

				ig2.setColor(new Color(image.getRGB(x * divider, y * divider)));
				ig2.fillRect(x * (sfPlus) + borderWidth, y * (sfPlus) + borderWidth, scaleFactor, scaleFactor);
			}

			ig2.setColor(contrast);
			ig2.fillRect(outWidth - borderWidth, y * sfPlus + borderWidth, borderWidth, scaleFactor);
		}	

		ig2.setColor(contrast);
		ig2.fillRect(0, outHeight - borderWidth, outWidth, borderWidth);

		ImageIO.write(bi, "PNG", new File(args[1]));
	}

}