package sustech.mse.simplecae.Element;
import Juma.*;
import sustech.mse.simplecae.Node.BaseNode;
import sustech.mse.simplecae.Shape.LinearShape1D;
public class Beam  extends BaseElement {
	  double E=1.0*1000000;
	  double Iz=1;
	  double Length=1; //length of each element
	  LinearShape1D linearShape1D;
	    public Matrix StiffMatrix; //matrix of B
	    private double [][] stiffmatrix=new double[4][4];//B
	    private LinearShape1D[] linearShape1Ds;
	   // public  double stiffnessConstant D = E/L;
	    public Matrix K;
    public Beam(BaseNode[] nodes,int Length){
        this.nodes=nodes;
        this.Length=Length;
        int i=0;
        for (BaseNode node:
             nodes) {
            node.localnumber=i;
            i++;
            node.elems.add(this);

        }
         linearShape1D=new LinearShape1D(this.nodes);
        linearShape1Ds=linearShape1D.getLinearShape1Ds();
        StiffMatrix=getStiffmatrix();
    }
    public Matrix getStiffmatrix(){

        int i=0;
     
            stiffmatrix[0][0]=12;
            stiffmatrix[0][1]=6*this.Length;
            stiffmatrix[0][2]=-12;
            stiffmatrix[0][3]=6*this.Length;

            stiffmatrix[1][0]=6*this.Length;
            stiffmatrix[1][1]=4*Math.pow(this.Length, 2);
            stiffmatrix[1][2]=-6*this.Length;;
            stiffmatrix[1][3]=2*Math.pow(this.Length, 2);

            stiffmatrix[2][0]=-12;
            stiffmatrix[2][1]=-6*Math.pow(this.Length, 1);
            stiffmatrix[2][2]=12;
            stiffmatrix[2][3]=-6*Math.pow(this.Length, 1);
            
            stiffmatrix[3][0]=6*this.Length;
            stiffmatrix[3][1]=2*Math.pow(this.Length, 2);
            stiffmatrix[3][2]=-6*this.Length;
            stiffmatrix[3][3]=4*Math.pow(this.Length, 2);

           

        //    i++;
       // }
       Matrix ret=new Matrix(stiffmatrix);
     
   	 
       return ret;
        

    }
    public Matrix getK(){
        //trans B * D *B  *A   % A is the area of triangle=this.linearShape1D.getNormal()/2

    double constant=E*Iz/Length/Length/Length;
        return K= StiffMatrix.times(constant);
    }

}
