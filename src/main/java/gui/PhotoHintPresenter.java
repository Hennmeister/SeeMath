package gui;
import java.io.*;
import java.util.Base64;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;

import javax.imageio.ImageIO;

public class PhotoHintPresenter {

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
