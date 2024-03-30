import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Random;

public class App
{
    public static void main(String[] args) throws Exception
    {
        LocalDateTime ldt = LocalDateTime.now();

        if (!(args.length > 1 && args[1].equals("-f")))
        {
            if (!ldt.getMonth().equals(Month.APRIL) || ldt.getDayOfMonth() != 1)
            {
                System.out.println("Not yet april 1st, activating stealth mode");
            }
            
            while (!ldt.getMonth().equals(Month.APRIL) || ldt.getDayOfMonth() != 1)
            {
                // Wait for april fools day
            }
        }
        
        switch (args[0])
        {
            case "wiggle":
            {
                long startTime = System.currentTimeMillis();
                final double timescale = 1000.0;
                final double movescale = 4.0;
                final double crrectionX = 0.5;
                final double crrectionY = 0.5;

                Random random = new Random();
                SimplexNoise noise = new SimplexNoise(random.nextInt(234));

                Robot robot = new Robot();

                boolean cont = true;
                while (cont)
                {
                    Point pos = MouseInfo.getPointerInfo().getLocation();

                    double x = (noise.noise((System.currentTimeMillis() - startTime) / timescale, 0) * movescale + crrectionX);
                    double y = (noise.noise(0, (System.currentTimeMillis() - startTime) / timescale) * movescale + crrectionY);

                    System.out.println(x + ", " + y + ", " + (System.currentTimeMillis() - startTime) / timescale);

                    robot.mouseMove((int)(pos.getX() + x), (int)(pos.getY() + y));

                    Thread.sleep(random.nextInt(10, 100));
                }
            }
            break;

            case "velocity":
            {
                double velX = 0.0;
                double velY = 0.0;

                double prevX = 100.0;
                double prevY = 100.0;

                double posX = 0.0;
                double posY = 0.0;

                final double friction = 1.0 - 0.001;
                final double controlThreshold = 0.1;

                Robot robot = new Robot();

                boolean cont = true;
                while (cont)
                {
                    PointerInfo pointer = MouseInfo.getPointerInfo();

                    if (Math.abs(posX - pointer.getLocation().getX()) > controlThreshold || Math.abs(posY - pointer.getLocation().getY()) > controlThreshold)
                    {
                    }
                    else
                    {
                        posX = pointer.getLocation().getX();
                        posY = pointer.getLocation().getY();

                        velX = posX - prevX;
                        velY = posY - prevY;
                        
                        velX *= friction;
                        velY *= friction;
    
                        // posX += velX;
                        // posY += velY;
    
                        System.out.println(velX + ", " + velY);
    
                        robot.mouseMove((int)(posX + velX), (int)(posY + velY));
                    }

                    prevX = posX;
                    prevY = posY;
                    
                    Thread.sleep(5);
                }
            }
            break;

            case "ghost_keyboard":
            {
                Robot robot = new Robot();

                Random random = new Random();

                while (true)
                {
                    final int key = random.nextInt(37, 90);
                    robot.keyPress(key);
                    robot.keyRelease(key);

                    Thread.sleep(random.nextInt(1000, 15000));
                }
            }
            
            case "funny_phrases":
            {
                Robot robot = new Robot();
                
                Random random = new Random();
                
                robot.keyRelease(KeyEvent.VK_SPACE);
                
                while (true)
                {
                    Thread.sleep(random.nextInt(1000 * 60 * 1, 1000 * 60 * 4));
                    // Thread.sleep(random.nextInt(8000, 1000 * 10));

                    String[] phrases = {
                        " April Fools!",
                        " Haha! April Fools!",
                        " Haha! April Fools!",
                        " Haha! April Fools!",
                        " Haha! April Fools!",
                        " Haha! April Fools!",
                        " Your sink has been unplugged",
                        " Your skunk has been unplugged",
                        " I belive that 1 + 4 = 7",
                        " Is it yesterday today?",
                        " The elevator is now voice-activated.",
                        " All the clocks have been set 3 and a half hours ahead.",
                        " Your phone's language setting has been changed to Klingon.",
                        " The coffee machine now dispenses hot chocolate instead.",
                        " All the pens have been replaced with disappearing ink.",
                        " The chairs have been replaced with bouncy exercise balls.",
                        " All the staplers are now shooting out confetti instead of staples.",
                        " The Wi-Fi password has been changed to 'AprilFools123'.",
                        " The printer is now printing everything in rainbow colors.",
                        " Your computer screen has been flipped upside down.",
                        " The world has been invaded by rubber ducks.",
                        " All the photos in the universe have been replaced with pictures of highland cows",
                        " Your chair has been replaced with a whoopee cushion."
                    };

                    String phrase = phrases[random.nextInt(phrases.length)];

                    for (char c : phrase.toCharArray())
                    {
                        int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
                        robot.keyPress(keyCode);
                        robot.keyRelease(keyCode);

                        // Thread.sleep(random.nextInt(80, 200));
                    }
                }
            }

            default:
                System.out.println(args[0] + " is not a valid prank id");
                break;
        }
    }
}
