package gui;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class ImageSaver implements  SaveGateway {
    @Override
    public void saveVisualization(Pane pane) {
            WritableImage writableImage = pane.snapshot(new SnapshotParameters(), null); //create a WritableImage to store the snapshot of the pane
            BufferedImage image =  SwingFXUtils.fromFXImage(writableImage, null);
            String timeStr = LocalTime.now().toString();
            timeStr = timeStr.replace("/", ":");
            File filePath = new File("./saved_visualizations/vis" + LocalDate.now() + "-" +
                    timeStr.substring(0, timeStr.indexOf(".")) + ".png");
            try { //try-catch needed for IOException
                ImageIO.write(image, "png", filePath);
            } catch (IOException e) { //catch IOException
                e.printStackTrace();
            }
    }
}
