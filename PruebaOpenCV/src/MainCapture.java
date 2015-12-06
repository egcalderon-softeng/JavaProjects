import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.oned.EAN13Reader;
import com.google.zxing.qrcode.QRCodeReader;

public class MainCapture {

	private static final int CV_CAP_PROP_FRAME_WIDTH = 3;
	private static final int CV_CAP_PROP_FRAME_HEIGHT = 4;

	public static void main(String[] args) throws IOException, LineUnavailableException, UnsupportedAudioFileException, InterruptedException {
		// creo la ventana y le agrego un label adentro de un panel
		MainCaptureForm window = new MainCaptureForm();
		ImageIcon imageIcon = new ImageIcon();
		JLabel jLabel = new JLabel(imageIcon);
        JPanel jPanel = new JPanel();
        jPanel.add(jLabel);
        window.getFrame().add(jPanel);
        String codigoResultante = "";
         
        File audio = new File("C:/Users/Eduardo/workspace/PruebaOpenCV/res/Heart_Monitor_Beep_Sound_Effects.wav");
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(audio);
        // Get a sound clip resource.
        Clip clip = AudioSystem.getClip();
        // Open audio clip and load samples from the audio input stream.
        clip.open(audioIn);
        
        // muestro todo
        jPanel.setVisible(true);
		window.getFrame().setVisible(true);
		
		// inicializo la camara
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    	VideoCapture camera = new VideoCapture(0);
    	camera.set(CV_CAP_PROP_FRAME_WIDTH, 800);
    	camera.set(CV_CAP_PROP_FRAME_HEIGHT, 600);
    			
    	if(!camera.isOpened()){
    		// me fijo si hubo un error en la inicializacion de la camara
    		System.out.println("Error en la creacion de la camara");
    	}
    	else {

    		Mat cuadro = new Mat();
    		EAN13Reader qrCodeReader = new EAN13Reader();
    		
    		// leo el primer cuadro para definir el tamaño de la ventana y del buffer
    		camera.read(cuadro); 
    		BufferedImage image = new BufferedImage(cuadro.width(), cuadro.height(), BufferedImage.TYPE_INT_RGB);
            window.getFrame().setSize(cuadro.width()+20, cuadro.height()+50);
            
            // seteo el primer cuadro ocultando, y mostrando el panel para que se renderice de nuevo
            getBufferedImageFrom(cuadro, image);
            imageIcon.setImage(image);
	    	jPanel.setVisible(false);
	    	jPanel.setVisible(true);
	    		    	
	    	// mientras no se cierre la aplicacion leo cuadro a cuadro
    	    while (true){
    	    	
    	    	camera.read(cuadro);   
    	    	
    	    	getBufferedImageFrom(cuadro, image);
    	    	imageIcon.setImage(image);
    	    	jPanel.repaint();
    	    	
				Result resultado;
				
				try {
					resultado = qrCodeReader.decode(getBinaryBitmapImageFrom(image));

					codigoResultante = resultado.toString();
			    	clip.start();
			    	TimeUnit.SECONDS.sleep(1);
					break;
				} catch (NotFoundException e) {
					
				} catch (FormatException e) {
					
				} 
    	    }
    	}
    	System.out.print("El codigo QR decodificado es: " + codigoResultante);
		
    	camera.release();
		window.getFrame().dispose();
	}	
	
	public static void getBufferedImageFrom(Mat cuadro, BufferedImage image){

    	WritableRaster raster = image.getRaster();
        byte[] px = new byte[3];
        int[] rgb = new int[3];    	
    	
        for (int y=0; y<cuadro.height(); y++) {
            for (int x=0; x<cuadro.width(); x++) {
            	cuadro.get(y,x,px);
                rgb[0] = px[2];
                rgb[1] = px[1];
                rgb[2] = px[0];
                raster.setPixel(x,y,rgb);
            }
        }
	}
	
	public static BinaryBitmap getBinaryBitmapImageFrom(BufferedImage image) {
    	return new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
	}
}
      
        