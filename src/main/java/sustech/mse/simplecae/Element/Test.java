package sustech.mse.simplecae.Element;

import Juma.*;
import sustech.mse.simplecae.Node.*;
import java.io.*;
import org.ujmp.core.Matrix;
import org.ujmp.core.SparseMatrix;
import org.ujmp.core.SparseMatrix2D;
import org.ujmp.core.calculation.Calculation;
import org.ujmp.core.util.DefaultSparseDoubleVector1D;
import sustech.mse.simplecae.Element.*;
import sustech.mse.simplecae.Node.BeamNode;
public class Test {

	public static int NodeNum = 4;
	public static int ElementNum = 3;
	public static BaseNode[] globalNode=new BaseNode[NodeNum];
	static Beam[] elems=new Beam[ElementNum];

	static Matrix globalK=SparseMatrix.Factory.zeros(2*NodeNum, 2*NodeNum);

	static Matrix globalF=SparseMatrix.Factory.zeros(2*NodeNum,1);
	static Matrix globalBC = SparseMatrix.Factory.zeros(2*NodeNum,1);

	/*
	 * public static void MatrixTest(){ MatrixTest ma=new MatrixTest(); ma.test(); }
*/
	public static void main(String[] args) {
	
		System.out.println("Hello World!");
		// MatrixTest();
		// initGlobalreadFile();
		beamtest();
	}
public static void initGlobal() {
		

		/*
		 * nodes[0]=new BaseNode(0,-1,1); nodes[1]=new BaseNode(2,0,1); nodes[2]=new
		 * BaseNode(0,1,1); nodes[0].uvw=new double[]{0.1,0.2,0}; nodes[0].number=0;
		 * nodes[1].number=1; nodes[2].number=2;
		 */
		//nodes in element 1
		BaseNode[] nodes = new BaseNode[2];
		nodes[0] = new BaseNode(0, 0, 0);
		nodes[1] = new BaseNode(1, 0, 0);
		nodes[0].uvw = new double[] { 0.1, 0.2, 0 };
		nodes[0].number = 0;
		nodes[1].number = 1;
		globalNode[nodes[0].number] = nodes[0];
		globalNode[nodes[1].number] = nodes[1];
	
		//nodes in element 2
		BaseNode[] nodes2 = new BaseNode[2];
		nodes2[0] = new BaseNode(1, 0, 1);
		nodes2[1] = new BaseNode(2, 0, 1);
		nodes2[0].uvw = new double[] { 0.1, 0.2, 0 };
		nodes2[0].number = 1;
		nodes2[1].number = 2;
		globalNode[nodes2[0].number] = nodes2[0];
		globalNode[nodes2[1].number] = nodes2[1];
		 
		//nodes in element 3
		BaseNode[] nodes3 = new BaseNode[2];
		nodes3[0] = new BaseNode(2, 0, 1);
		nodes3[1] = new BaseNode(3, 0, 1);
		nodes3[0].uvw = new double[] { 0.1, 0.2, 0 };
		nodes3[0].number = 2;
		nodes3[1].number = 3;
		globalNode[nodes3[0].number] = nodes3[0];
		globalNode[nodes3[1].number] = nodes3[1];
		
		
		elems[0] = new Beam(nodes,1);
		elems[1] = new Beam(nodes2,1);
		elems[2] = new Beam(nodes3,1);

		assemble();
	}


	public static void beamtest() {
		 initGlobal();
		//initGlobalreadFile();
		//initGlobalBC();
		 System.out.println(globalK);
		System.out.println("det:" + globalK.det());
		setBC(1,0);
		//setBC(2,0);
		setBC(1,3);
		//setBC(2,3);
		
		double[][] globalarray = globalK.toDoubleArray();
		Juma.Matrix jmatr = new Juma.Matrix(globalarray);
		// Juma.Matrix method
		// subm=jmatr.getMatrix(NodeNum/2,2*NodeNum-1,NodeNum/2,2*NodeNum-1);
		// System.out.println(subm);
		// double[][] f=new double[][]{{5000},{0},{5000},{0}};
		// Juma.Matrix f= Juma.Matrix.random(2*NodeNum-NodeNum/2,1);
		// subm;
		// Juma.Matrix rs=subm.solve(f);

		//Juma.Matrix subm = jmatr.getMatrix(0, 2 * NodeNum - 1, 0, 2 * NodeNum - 1);
		//Juma.Matrix f = Juma.Matrix.random(2 * NodeNum, 1);
		//double[][] ff = f.getArray();
		
		Matrix fff =SparseMatrix.Factory.ones(2 * NodeNum, 1);
		Matrix rs = globalK.solve(fff);

		for (int i = 0; i <2* NodeNum; i++) {
			for (int j = 0; j < 1; j++)
				System.out.print(i + ":" + rs.toDoubleArray()[i][j] + "\n");
		}
		//System.out.println(rs);
		
         //method 2 solution
		// Matrix rs= globalK.inv().times(globalF.subMatrix(Calculation.Ret.LINK,4,7));
		 //System.out.println(rs);
		// System.out.println(ele.getLinearShape2Ds()[1].getValue(1,3));
		// System.out.println(ele.getLinearShape2Ds()[2].getValue(1,3));
	}
  // (1, 0, 0) stand for 1 constraint(x-direction) for row,column=(0 0);
	//(2, 0, 0) stand for 2 constraint(y-direction)
	////(3, 0, 0) stand for 3 constraint(z-rotation)
	/*public static void initGlobalBC() {
		globalBC.setAsInt(1, 0, 0);
		globalBC.setAsInt(2, 1, 0);
		globalBC.setAsInt(1, 2, 0);
		globalBC.setAsInt(2, 3, 0);
	}
	*/

	public static double bigNum = 1e20;
  //set related BC point to bignum
	public static void setBC(int constraint, int i) {
		globalBC.setAsInt(constraint, i, 0);
	
			if (globalBC.getAsInt(i, 0) ==1) {  //disp in y is 0;
				globalK.setAsDouble(bigNum, 2 * i, 2 * i); 
			
				// globalK.deleteRows(Calculation.Ret.NEW,i);
				// globalK.deleteColumns(Calculation.Ret.NEW,i);
				// globalF.deleteRows(Calculation.Ret.NEW,i);

			}
			if (globalBC.getAsInt(i, 0) ==2) {////mometum  is 0;
				
				globalK.setAsDouble(bigNum, 2 * i + 1, 2 * i + 1);  
			

			}
	}

	public static void initGlobalF(int i) {
		globalF.setAsFloat(5000, i, 0);
		//globalF.setAsFloat(0, 5, 0);
		//globalF.setAsFloat(5000, 6, 0);
		//globalF.setAsFloat(0, 7, 0);
	}

	public static void assemble() {
		for (Beam ele : elems) {
			// System.out.println(ele.getK().det());
			// ele.getK().print(1,1);
			BaseNode[] enodes = ele.getNodes();

			// int i=0;
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 2; j++) {

					 globalK.setAsDouble(globalK.getAsDouble(2 * enodes[i].number, 2 * enodes[j].number)
                             + ele.getK().get(2 * enodes[i].localnumber, 2 * enodes[j].localnumber),
                     2 * enodes[i].number, 2 * enodes[j].number);

             globalK.setAsDouble(globalK.getAsDouble(2 * enodes[i].number, 2 * enodes[j].number+1)
                             + ele.getK().get(2 * enodes[i].localnumber, 2 * enodes[j].localnumber+1),
                     2 * enodes[i].number, 2 * enodes[j].number+1);

             globalK.setAsDouble(globalK.getAsDouble(2 * enodes[i].number+1, 2 * enodes[j].number)
                             + ele.getK().get(2 * enodes[i].localnumber+1, 2 * enodes[j].localnumber),
                     2 * enodes[i].number+1, 2 * enodes[j].number);

             globalK.setAsDouble(globalK.getAsDouble(2 * enodes[i].number + 1, 2 * enodes[j].number + 1)
                             + ele.getK().get(2 * enodes[i].localnumber + 1, 2 * enodes[j].localnumber + 1),
                     2 * enodes[i].number + 1, 2 * enodes[j].number + 1);

				}
			}
			// System.out.print(globalK);
		}
	}
	public static void initGlobalreadFile() {
		File f = new File("data.txt");
		BufferedReader bf;
		String ss;
		try {
			bf = new BufferedReader(new FileReader(f));
			int i = 0, j = 0;
			while ((ss = bf.readLine()) != null && ss != " ") {

				if (ss.contains("node")) {
					NodeNum = Integer.parseInt(ss.split(" ")[1]);
					System.out.println("NodeNum:" + NodeNum);
					globalNode = new BaseNode[NodeNum];
					globalK = SparseMatrix.Factory.zeros(2 * NodeNum, 2 * NodeNum);

					globalF = SparseMatrix.Factory.zeros(2 * NodeNum, 1);
					globalBC = SparseMatrix.Factory.zeros(2 * NodeNum, 1);
				} else if (i < NodeNum) {
					String[] nodestr = ss.split("\t");
					globalNode[Integer.parseInt(nodestr[0]) - 1] = new BaseNode(Double.parseDouble(nodestr[1]),
							Double.parseDouble(nodestr[2]), 1);
					i++;
				}

				if (ss.contains("element")) {
					ElementNum = Integer.parseInt(ss.split(" ")[1]);
					System.out.println(ElementNum);
					elems = new Beam[ElementNum];
				} else if (j < ElementNum) {
					if (j == 142)
						System.out.println(j);
					String[] elemstr = ss.split("\t");
					BaseNode[] nodes = new BaseNode[3];
					nodes[0] = globalNode[Integer.parseInt(elemstr[1]) - 1];
					nodes[0].number = Integer.parseInt(elemstr[1]) - 1;
					nodes[1] = globalNode[Integer.parseInt(elemstr[2]) - 1];
					nodes[1].number = Integer.parseInt(elemstr[2]) - 1;
					nodes[2] = globalNode[Integer.parseInt(elemstr[3]) - 1];
					nodes[2].number = Integer.parseInt(elemstr[3]) - 1;
					elems[Integer.parseInt(elemstr[0]) - 1] = new Beam(nodes,1);

					j++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		assemble();
	}

	
}
