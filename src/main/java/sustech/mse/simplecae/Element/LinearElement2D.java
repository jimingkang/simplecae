package  sustech.mse.simplecae.Element;

import Juma.*;
import  sustech.mse.simplecae.Node.BaseNode;
import  sustech.mse.simplecae.Shape.LinearShape2D;
/**
 * Created by jimmy on 6/20/19.
 */
public class LinearElement2D extends BaseElement {
    double E=30.0*1000000;
    double poison=0.3;
    double[][] dd = {{1.,poison,0},{poison,1,0.},{0,0,(1-poison)/2.0}};
    LinearShape2D linearShape2D;
    private LinearShape2D[] linearShape2Ds;
    private double [] disp=new double[2];
    private double [] strain=new double[3];
    private double [][] stiffmatrix=new double[3][6];//B
    public Matrix StiffMatrix; //matrix of B
   // public Matrix Transposestiffmatrix;  //transpose B


    public Matrix D = new Matrix(dd).times(E/(1-poison*poison));
    public Matrix K;
    public LinearElement2D(BaseNode[] nodes){
        this.nodes=nodes;
        int i=0;
        for (BaseNode node:
             nodes) {
            node.localnumber=i;
            i++;
            node.elems.add(this);

        }
         linearShape2D=new LinearShape2D(this.nodes);
        linearShape2Ds=linearShape2D.getLinearShape2Ds();
        setStiffmatrix();
    }
    public LinearShape2D[] getLinearShape2Ds(){

        return linearShape2Ds;
    }
    public double[] getDisp(double x,double y){
        double[] dis=new double[2];
        int i=0;
        for (BaseNode node: this.nodes             ) {

            dis[0]+=linearShape2Ds[i].getValue(x,y)*linearShape2Ds[i].nodes[i].uvw[0]/linearShape2Ds[i].getNormal();
            dis[1]+=linearShape2Ds[i].getValue(x,y)*linearShape2Ds[i].nodes[i].uvw[1]/linearShape2Ds[i].getNormal();

            i++;
        }
        return this.disp=dis;
    }
    public double[] getStrain(double x,double y){
        double[] strain=new double[3];
        int i=0;
        for (BaseNode node: this.nodes             ) {

            strain[0]+=linearShape2Ds[i].getBelta(i)*linearShape2Ds[i].nodes[i].uvw[0]/linearShape2Ds[i].getNormal();
            strain[1]+=linearShape2Ds[i].getGamma(i)*linearShape2Ds[i].nodes[i].uvw[1]/linearShape2Ds[i].getNormal();
            strain[3]+=linearShape2Ds[i].getBelta(i)*linearShape2Ds[i].nodes[i].uvw[1]/linearShape2Ds[i].getNormal()+
                    linearShape2Ds[i].getGamma(i)*linearShape2Ds[i].nodes[i].uvw[0]/linearShape2Ds[i].getNormal();

            i++;
        }
        return this.strain=strain;
    }
    public void setStiffmatrix(){

        int i=0;
        for (BaseNode node: this.nodes             ) {

            stiffmatrix[0][2*i]=linearShape2D.getBelta(i)/linearShape2Ds[i].getNormal();
            stiffmatrix[0][2*i+1]=0;

            stiffmatrix[1][2*i]=0;
            stiffmatrix[1][2*i+1]=linearShape2D.getGamma(i)/linearShape2Ds[i].getNormal();

            stiffmatrix[2][2*i]=linearShape2D.getGamma(i)/linearShape2Ds[i].getNormal();
            stiffmatrix[2][2*i+1]=linearShape2D.getBelta(i)/linearShape2Ds[i].getNormal();

            i++;
        }
         StiffMatrix=new Matrix(stiffmatrix);
        //  Transposestiffmatrix=StiffMatrix.transpose();

    }
    public Matrix getK(){
        //trans B * D *B  *A   % A is the area of triangle=this.linearShape2D.getNormal()/2

      //  D.print(1,1);
        //System.out.println();
      //  StiffMatrix.print(1,1);
        return K= StiffMatrix.transpose().times(D).times(StiffMatrix).times(this.linearShape2D.getNormal()/2);
    }

}
