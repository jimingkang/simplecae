package sustech.mse.simplecae.Shape;

/**
 * Created by jimmy on 6/20/19.
 */
import sustech.mse.simplecae.Node.BaseNode;
public class LinearShape2D {
  public BaseNode[] nodes;
    private double normal; //length or  area
private double alpha,belta,gamma;
    private LinearShape2D[] linearShape2Ds;

    public LinearShape2D(BaseNode[]nodes){
        this.nodes=nodes;

    }

    public LinearShape2D() {

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
    public LinearShape2D[] getLinearShape2Ds(){
        LinearShape2D[] LinearShape2Ds=new LinearShape2D[3];
        int i=0;
        for (BaseNode node: this.nodes             ) {
            LinearShape2Ds[i]=new LinearShape2D(this.nodes);
            LinearShape2Ds[i].getAlpha(i);
            LinearShape2Ds[i].getBelta(i);
            LinearShape2Ds[i].getGamma(i);
            i++;
        }
        return LinearShape2Ds;
    }
}
