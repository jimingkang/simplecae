package sustech.mse.simplecae.Shape;

/**
 * Created by jimmy on 6/20/19.
 */
import sustech.mse.simplecae.Node.BaseNode;
public class LinearShape1D {
  public BaseNode[] nodes;
    private double normal; //length or  area
private double alpha,belta,gamma;
    private LinearShape1D[] linearShape1Ds;

    public LinearShape1D(BaseNode[]nodes){
        this.nodes=nodes;

    }

    public LinearShape1D() {

    }

    public double getAlpha(int i){  // i start from 0
        int modby=this.nodes.length;
        double tmp=this.nodes[(i+1)%modby].x*this.nodes[(i+2)%modby].y-this.nodes[(i+1)%modby].y*this.nodes[(i+2)%modby].x;

        return this.alpha=tmp;
    }
    public double getBelta(int i){
        int modby=this.nodes.length;
        double tmp=this.nodes[(i+1)%modby].y-this.nodes[(i+2)%modby].y;
        return this.belta=tmp;
    }
    public double getGamma(int i){
        int modby=this.nodes.length;
        double tmp=this.nodes[(i+2)%modby].x-this.nodes[(i+1)%modby].x;
        return this.gamma=tmp;
    }
    public double getNormal(){
        double tmp=this.nodes[0].x*this.getBelta(0)+this.nodes[1].x*this.getBelta(1)+this.nodes[2].x*this.getBelta(2);
        if(tmp<0)
        {
           BaseNode local=  nodes[1];
            nodes[1]=nodes[2];
            nodes[2]=local;
          /*int local=  nodes[1].localnumber;
            nodes[1].localnumber=nodes[2].localnumber;
            nodes[2].localnumber=local;*/
            tmp=this.nodes[0].x*this.getBelta(0)+this.nodes[1].x*this.getBelta(1)+this.nodes[2].x*this.getBelta(2);
          //  System.out.println("nomal:"+tmp);
        }
        return this.normal=tmp;
    }
    public double getValue(double x, double y){
        return this.alpha+this.belta*x+this.gamma*y;
    }
    public LinearShape1D[] getLinearShape1Ds(){
        LinearShape1D[] LinearShape1Ds=new LinearShape1D[3];
        int i=0;
        for (BaseNode node: this.nodes             ) {
            LinearShape1Ds[i]=new LinearShape1D(this.nodes);
            LinearShape1Ds[i].getAlpha(i);
            LinearShape1Ds[i].getBelta(i);
            LinearShape1Ds[i].getGamma(i);
            i++;
        }
        return LinearShape1Ds;
    }
}
