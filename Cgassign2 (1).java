package org.yourorghere;

import com.sun.opengl.util.Animator;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;
import java.util.Stack;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;



/**
 * Cgassign2.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel) <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class Cgassign2  {

    public static void main(String[] args) {
        Scanner sc =new Scanner(System.in);
        GLCapabilities cap=new GLCapabilities();
        final GLCanvas canvas=new GLCanvas(cap);
        System.out.println("Enter the number of vertices in scene");
        int m=sc.nextInt();
       // int[][] Vertex=new int[m][3];
        //System.out.println(Vertex[0][0]);
        System.out.println("Enter number of polygons");
        int N=sc.nextInt();
        int polygon[][]=new int[N][11];
        int[][] Vertex={{20,120,20},{120,120,20},{120,40,20},{20,40,20},{40,80,30},{80,80,30},{80,40,30},{40,40,30},{80,220,20},{120,180,20},{40,180,20}};
        int s3=0;
        for(int i=0;i<N;i++){
            System.out.println("Enter the number vertices of polygon");
            int s=sc.nextInt();
            polygon[i][0]=s3;
            polygon[i][1]=s;
            System.out.println("Enteer color:");
            polygon[i][2]=sc.nextInt();
            //System.out.println(s);
            int s2=checkSize(Vertex);
            s3=s+s3;
            
            
        }
        for(int i=0;i<N;i++){
           // for()
            polygon=equation_plane(Vertex,polygon[i][0],polygon[i][1],i,polygon);
            polygon=bondingVal(polygon,Vertex,i,polygon[i][0],polygon[i][1]);
        }
        int window[]=new int[3];
        Stack<int[]> Win=new Stack<int[]>();
        System.out.println("Enter Xorigin,Yorigin,Size of window");
        int xori=sc.nextInt();
        window[0]=xori;
        int yori=sc.nextInt();
        window[1]=yori;
        int size=sc.nextInt();
        window[2]=size;
       // Win.push(window);
        ThirdGLEventListener b=new ThirdGLEventListener(Vertex,polygon,N,window,Win);
        canvas.addGLEventListener(b);
        final JFrame frame=new JFrame("Reflection");
        frame.add(canvas);
        frame.setSize(1280,720);
        final Animator animator = new Animator(canvas);
        
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {

                    public void run() {
                        
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        // Center frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
        
       
}
    static int[][] bondingVal(int[][] polygon,int[][] vertex,int i,int s,int size){
//        if(s!=0)
//            s--;
        int xmin=vertex[s][0],xmax=vertex[s][0],ymin=vertex[s][1],ymax=vertex[s][1];
        for(int j=s;j<s+size;j++){
            if(vertex[j][0]>=xmax)
                xmax=vertex[j][0];
            if(vertex[j][0]<=xmin)
                xmin=vertex[j][0];
            if(vertex[j][1]>=ymax)
                ymax=vertex[j][1];
            if(vertex[j][1]<=ymin)
                ymin=vertex[j][1];
               
        }
        polygon[i][7]=xmin;
        polygon[i][8]=xmax;
        polygon[i][9]=ymin;
        polygon[i][10]=ymax;
        
        return polygon;
    }
     public static int[][] equation_plane(int[][] vertex,int s,int size,int i,int[][] polygon)
{
  
    int a1 = vertex[s+1][0] - vertex[s][0];
    int b1 = vertex[s+1][1] - vertex[s][1];
    int c1 = vertex[s+1][2] - vertex[s][2];
    int a2 = vertex[s+2][0] - vertex[s][0];
    int b2 = vertex[s+2][1] - vertex[s][1];
    int c2 = vertex[s+2][2] - vertex[s][2];
    polygon[i][3] = b1 * c2 - b2 * c1;
    polygon[i][4] = a2 * c1 - a1 * c2;
    polygon[i][5] = a1 * b2 - b1 * a2;
    polygon[i][6] = (- polygon[i][4] * vertex[s][0] - polygon[i][5]  * vertex[s][1] - polygon[i][6] * vertex[s][2]);
    return polygon;
   
//    System.out.println("equation of plane is " + a +
//                       " x + " + b + " y + " + c +  
//                       " z + " + d + " = 0.");
}
     static int checkSize(int[][] vertex){
        int k=0;
        for(int i=0;i<vertex.length;i++){
            if(vertex[i][0]!=0 || vertex[i][1]!=0  || vertex[i][2]!=0)
                k++;
        }
        return k;
    }
}
class ThirdGLEventListener implements GLEventListener {
    private GLU glu;
    int[][] Vertex,polygon;
    int i=0,N;
    int window[];
    Stack<int[]> Win;
    public ThirdGLEventListener(int[][] Vertex, int[][] polygon, int N, int[] window,Stack<int[]> Win) {
        this.Vertex = Vertex;
        this.polygon = polygon;
        this.N = N;
        this.window = window;
        this.Win=Win;
    }
    
    public void init(GLAutoDrawable gld) {
        GL gl = gld.getGL();
        glu = new GLU();
        gl.setSwapInterval(1);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glViewport(0,0,320,320);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(0,320,0,320);
    }
    int x=5;
    int y=160;
    int x2=160,y2=5;
    int xtend=320,ytend=320;
    public void display(GLAutoDrawable drawable) {
//        i++;
//        System.out.println("Display call number - " + i);
       
        GL gl = drawable.getGL();

        //gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glColor3f(1.0f, 1.0f, 1.0f );
        gl.glLineWidth(6.0f);
        
        if(x<=xtend){
            gl.glBegin(GL.GL_LINES);
                gl.glVertex2i(0, y);
                gl.glVertex2i(x,y);
            gl.glEnd();
            x += 1;
            if(x==xtend && xtend>80)
            {
                
                x=5;
                y=y/2;
                xtend=xtend/2;
                
            }
        }
            
         if(y2<=ytend){
            gl.glBegin(GL.GL_LINES);
                gl.glVertex2i(x2, 0);
                gl.glVertex2i(x2,y2);
            gl.glEnd();
            y2 += 1;
            if(y2==ytend && ytend>80)
            {
                y2=5;
                x2=x2/2;
                ytend=ytend/2;
            }
            }
        long startTime =  System.nanoTime();
        DrawShape( gl, Vertex, polygon, N);
       
                
        if(xtend==80 && x==80)    
            warnock(gl, Vertex, polygon, N, window, Win);
        
        //drawLine(gl,Vertex);
        long endTime = System.nanoTime();
        System.out.println("Time required = " + (double)(endTime - startTime)/1000000 + " ms");
gl.glFlush();
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width,
            int height) {
    }

    public void displayChanged(GLAutoDrawable drawable,
            boolean modeChanged, boolean deviceChanged) {
    }
  public void dispose(GLAutoDrawable arg0) {

    }
  //Draws the outline of Polygon that are used in the program
  void DrawShape(GL gl,int[][] Vertex,int[][] polygon,int N)
  {  
      for(int i=0;i<N;i++){
          System.out.println("Figure:-"+i);
          System.out.println("polygon:-"+polygon[i][0]+" yo "+polygon[i][1]);
          for(int j=polygon[i][0];j<polygon[i][0]+polygon[i][1];j++){
              System.out.println("Times:-"+j);
              if(j==polygon[i][0]+polygon[i][1]-1)
                  bresenhamLineAlgorithm(gl,new Point(Vertex[j][0],Vertex[j][1]),new Point(Vertex[polygon[i][0]][0],Vertex[polygon[i][0]][1]),"thick",polygon[i][2]);
              else
                bresenhamLineAlgorithm(gl,new Point(Vertex[j][0],Vertex[j][1]),new Point(Vertex[j+1][0],Vertex[j+1][1]),"thick",polygon[i][2]);
          }
  }
  }
 // Bresenham Line Drawing Algorithm     
   private void bresenhamLineAlgorithm(GL gl,Point p1,Point p2,String lineType,int n){
        int x = p1.x;
        int y = p1.y;
        int dx = Math.abs(p2.x-p1.x);
        int dy = Math.abs(p2.y-p1.y);
        int s1 = sign(p2.x-p1.x);
        int s2 = sign(p2.y-p1.y);
        int interchange = 0;
         if(n==1)
                gl.glColor3f(1.0f,0.0f,1.0f);
            if(n==2)
                gl.glColor3f(1.0f,1.0f,0.0f);
            if(n==3)
                gl.glColor3f(0.0f,0.0f,1.0f);

        if(dy>dx){
            int temp = dx;
            dx = dy;;
            dy = temp;
            interchange = 1;
        }
        else
            interchange = 0;

        int e1 = 2*dy-dx;
        gl.glLineWidth(6.0f);
        gl.glBegin(GL.GL_POINTS);
        
        for(int i=0;i<=dx;i++){
            if(lineType.equals("normal"))
                gl.glVertex2i(x, y);

            else if(lineType.equals("dotted")){
                if(i%5==0){
                    gl.glVertex2i(x,y);
                    //System.out.println(" i=" + i+ "coordinates-" +x + "," + y);
                }
            }
            else if(lineType.equals("dashed")){
                if(i%10<4 || i%10 > 6){
                    gl.glVertex2i(x,y);
                }
            }

            else if(lineType.equals("thick")){
                {
                    gl.glVertex2i(x-2,y-2);
                    gl.glVertex2i(x-1,y-1);
                    gl.glVertex2i(x,y);
                    gl.glVertex2i(x+1,y+1);
                }
            }
            while(e1>0){
                if(interchange==1)
                    x = x + s1;
                else
                    y = y + s2;

                e1 = e1 - 2*dx;;
            }

            if(interchange == 1)
                y = y + s2;
            else
                x = x + s1;

            e1 = e1 + 2 * dy;
        }
        gl.glEnd();
    }
  //Warnock Algo
  public void warnock(GL gl,int[][] Vertex,int[][] polygon,int N,int[] win2,Stack<int[]> Win){
      Win.push(win2);
      while(!Win.isEmpty()){
          int[] window=Win.pop();
          
          int i=0,disjoint=0;
          while(i<N && disjoint==0){
              disjoint=Box(i,polygon,window,disjoint);
              i++;
          }
          if(disjoint>0){
              int size=window[2];
              if(size>1){
                  
                  size=size/2;
                  Win.push(window2(window[0]+size,window[1]+size,size));
                  Win.push(window2(window[0],window[1]+size,size));
                  Win.push(window2(window[0]+size,window[1],size));
                  Win.push(window2(window[0],window[1],size));
              }
              else{
                  int pnum=-1;
                  
                  pnum=cover(Vertex,N,polygon,window,pnum);
                  if(pnum>-1){
                      Display2(window,polygon[pnum][2],gl);
                  }
              }
          }
  }
  }
  
  //Displays the Final output of warnock algo
  void Display2(int[] window,int n,GL gl){
        
         gl.glBegin(GL.GL_POINTS);
         gl.glColor3f(1.0f,1.0f,1.0f);
        if(n<=3){
            
            if(n==1)
                gl.glColor3f(1.0f,0.0f,1.0f);
            if(n==2)
                gl.glColor3f(1.0f,1.0f,0.0f);
            if(n==3)
                gl.glColor3f(0.0f,0.0f,1.0f);
           
        for(int j=window[1];j<=window[1]+window[2]-1;j++){
            for(int i=window[0];i<=window[0]+window[2]-1;i++){
                gl.glVertex2i(i, j);
                
            }
        }
        }
           
        gl.glEnd();
    }
  //Computes which polygon is on the top
  int cover(int[][] vertex,int N,int[][] polygon,int[] window,int pnum){
      int zmax=0;
      pnum=-1;
      int x=window[0]+window[2]/2;
      int y=window[1]+window[2]/2;
      //Point p=new Point(x,y);
      for(int i=0;i<N;i++){
          
          int index=polygon[i][0];
//          if(i==0)
//              index=1;
          int j=0;
          int pvisible=0;
          Point P1,P2;
          while(j<polygon[i][1]-1 && pvisible>=0){
              P1=new Point(vertex[index][0],vertex[index][1]);
                P2=new Point(vertex[index+1][0],vertex[index+1][1]);
               
                pvisible=Visible(x,y,P1,P2,pvisible);
                
                index++;
                j++;
          }
          if(pvisible>=0){
              int k;
              k=polygon[i][0];
              
              
               P1=new Point(vertex[index][0],vertex[index][1]);
                 P2=new Point(vertex[polygon[i][0]][0],vertex[polygon[i][0]][1]);               
               pvisible=Visible(x,y,P1,P2,pvisible); 
               
                if(pvisible>=0){
                    int z=0;
                    
                    
                        z=vertex[polygon[i][0]][2];
                        if(z>zmax){
                            zmax=z;
                            pnum=i;
                        
                    }
                    
                }
          }
      }
      return pnum;
  }
  
  //Return if the points lies in the polygon or not
  int Visible(int x,int y,Point P1,Point P2,int pvisible){
        int temp1,temp2,temp3;
        temp1=(x-P1.x)*(P2.y-P1.y);
//        if(x==P1.x)
//         temp1=(P2.y-P1.y);
        temp2=(y-P1.y)*(P2.x-P1.x);
        temp3=temp1-temp2;
        pvisible= sign(temp3);
        return pvisible;
    }
   int sign(float m){
        if(m<0){
            return -1;
            }
       
        else if(m==0){  
            return 0;
        }
        else {
            return 1;
        }
    }
   //Returns the value of disjoint which further determines if polygon is present inside the window or not
  int Box(int i,int[][] polygon,int[] window,int disjoint){
      int xleft=window[0];
        int xright=window[0]+window[2]-1;
        int ybottom=window[1];
        int ytop=window[1]+window[2]-1;
        //System.out.println(k);
        disjoint=1;
        if(polygon[i][7]>xright) disjoint=0;
        if(polygon[i][8]<xleft) disjoint=0;
        if(polygon[i][9]>ytop) disjoint=0;
        if(polygon[i][10]<ybottom) disjoint=0;
        return disjoint;
  }
  int[] window2(int a,int b,int c){
        int[] win={a,b,c};
       
        return win;
    }
}