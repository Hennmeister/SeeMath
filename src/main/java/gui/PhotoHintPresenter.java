package gui;
import java.io.*;
import java.util.Base64;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;

import javax.imageio.ImageIO;

/**
 * This class is responsible for generating an image of the visualizations created by SeeMath to be sent to the Hypatia
 * editor.
 */

public class PhotoHintPresenter {

    /**
     * This method is responsible for generating the photoHint of the visualization pane to send to the Hypatia Editor.
     * Takes a snapshot of {@code pane}, and converts the snapshot into a .png file which is then encoded into a
     * Base64 string.
     * @param pane the pane storing the visualization to be converted into an image
     * @return A Base64 string which stores the image of the visualization.
     */

    public String getPhotoHint(Pane pane) {
        WritableImage writableImage; //create a WritableImage to store the snapshot of the pane
        ByteArrayOutputStream s = new ByteArrayOutputStream(); //this ByteArrayOutputStream is needed to get a byte[]
        writableImage = pane.snapshot(new SnapshotParameters(), null); //store the snapshot in writableImage
        try { //try-catch needed for IOException
            //convert writableImage into .png and store it in the ByteArrayOutputStream
            ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", s);
            s.close(); //close output stream
        } catch (IOException e) { //catch IOException
            e.printStackTrace();
        }
        //return a Base64 representation of the photoHint
        return Base64.getEncoder().encodeToString(s.toByteArray());
    }

}
