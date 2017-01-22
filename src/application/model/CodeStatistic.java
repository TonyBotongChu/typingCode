package application.model;

public class CodeStatistic
{
	// num of commits
	public int nZs;
	// num of direct codes (codes without commits)
	public int nDm;
	// num of blank lines
	public int nKh;
	// num of mixed codes (codes with commits)
	public int nGg;
	// num of functions
	public int nHs;
	
	public void printData()
	{
		System.out.println("注释： " + nZs);
	    System.out.println("代码： " + nDm);
	    System.out.println("空行： " + nKh);
	    System.out.println("公共： " + nGg);
	    System.out.println("函数： " + nHs);
	}
}
