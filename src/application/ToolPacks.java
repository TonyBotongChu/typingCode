/*
 * This class is for tools that may be used by other classes in this package.
 * By Zhu Botong
 */
package application;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import application.model.*;

public class ToolPacks
{
	public static String TAB2BLANK(String source)
	{
		// transfer tab to blanks, depending on Settings.getTABINC
		String s = "";
		for (int i = 0; i < source.length(); i++)
		{
			if (source.charAt(i) == '\t')
			{
				for (int j = 0; j < Settings.getTABINC(); j++)
				{
					s += " ";
				}
			}
			else
			{
				s += source.charAt(i);
			}
		}
		return s;
	}

	public static Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane)
	{
		Node result = null;
		ObservableList<Node> childrens = gridPane.getChildren();

		for (Node node : childrens)
		{
			if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column)
			{
				result = node;
				break;
			}
		}

		return result;
	}
	public static CodeStatistic codeStatistic(ArrayList<String> sourceFile)
	{
		// This function is transferred from c++. For unknown reasons, it will cause IndexOutOfRange error.
		CodeStatistic data = new CodeStatistic();
		int nZs = 0;
		int nDm = 0;
		int nKh = 0;
		int nGg = 0;
		int nHs = 0;
		int bSyh = 0, bXgx = 0, bHs = -1, bCode = 0, bZs = 0;
		if (!sourceFile.isEmpty())
		{
			int i;
			for (int j = 0; j < sourceFile.size(); j++)
			{
				String line = sourceFile.get(j);
				i = 0;
				bCode = 0;            //该行没有代码
		        bZs = 0;              //该行没有注释
		        if (bXgx != 0)        //bXgx 斜杠星注释标记
		            bZs = 1;          //该行有注释
		        //过滤无效符号
		        while (line.charAt(i) == ' ' || line.charAt(i) == '\t' || line.charAt(i) == '\r' || line.charAt(i) == '\n')
		        {
		            ++i;
		        }
		        //“以下为空行统计区域：开始”
		        if (bXgx == 0 && line.charAt(i) == '\0')  //空行
		        {
		            ++nKh;
		            continue;
		        }
		        //“空行统计：结束”
		        while (true)
		        {
		            //第一次遇到双引号              引号为非转义字符(\")
		            if (bSyh == 0 && line.charAt(i) == '\"' && ((i > 0 && line.charAt(i-1) != '\\') || (i == 0)))
		            {
		                ++i;
		                bSyh = 1;
		                continue;
		            }
		            //“正在进行双引号屏蔽....”
		            if (bSyh != 0)
		            {
		                //“ \”结束”
		                if (line.charAt(i) == '\"' && ((i > 0 && line.charAt(i-1) != '\\') || (i == 0)))
		                {
		                    bSyh = 0;
		                }
		                else if (line.charAt(i) == '\0')  //行末尾
		                {
		                    if (bZs != 0)
		                        ++nZs;
		                    if (bCode != 0)
		                        ++nDm;
		                    if (bZs != 0 && bCode != 0)
		                        ++nGg;
		                    break;
		                }
		                ++i;
		                continue;
		            }
		            //遇到单引号(避免'{','}')，且非转义字符\',连续跳过3个(第二个'后位置)
		            if (line.charAt(i) == '\'' && ((i > 0 && line.charAt(i-1) != '\\') || (i == 0)))
		            {
		                i += 3;
		                continue;
		            }
		            //“//注释行”
		            if (bXgx == 0 && line.charAt(i) == '/' && line.charAt(i+1) == '/')
		            {
		                if (bCode != 0)     //“前有代码，混合注释行”
		                {
		                    ++nZs;     //注释
		                    ++nDm;     //代码
		                    ++nGg;     //公共
		                }
		                else          //纯注释行
		                {
		                    ++nZs;
		                }
		                break;  //跳出当前行(即，内while循环),“//”后代码不做判断
		            }
		            //“/*注释开始”
		            if (bXgx == 0 && line.charAt(i) == '/' && line.charAt(i+1) == '*')
		            {
		                i += 2;        //跳过/*符号
		                bXgx = 1;      //标记“/*”开始
		                bZs = 1;       //“发现注释”
		                continue;
		            }
		            //“正在进行多行注释....”
		            if (bXgx != 0)
		            {
		                //“*/注释结束”
		                if (line.charAt(i) == '*' && line.charAt(i+1) == '/')
		                {
		                    ++i;     //“跳过*/”注意后有一个 ++i;
		                    bXgx = 0;
		                }
		                else if (line.charAt(i) == '\0')  //行末尾
		                {
		                    if (bCode != 0)       //注释前有代码，即“混合行”
		                    {
		                        ++nDm;
		                        ++nZs;
		                        ++nGg;
		                    }
		                    else
		                    {
		                        ++nZs;       //“纯注释”
		                    }
		                    break;
		                }
		                ++i;
		                continue;
		            }
		            if (line.charAt(i) == '\0')
		            {
		                if (bZs != 0)
		                    ++nZs;
		                if (bCode != 0)
		                    ++nDm;
		                if (bZs != 0 && bCode != 0)
		                    ++nGg;
		                break;
		            }
		            //“以下全是有效代码区域”
		            //“函数个数统计区域：开始”
		            if (line.charAt(i) == '{')      //记录函数左括号
		            {
		                ++bHs;
		            }
		            else if (line.charAt(i) == '}') //遇到函数右括号
		            {
		                if (bHs == 0)        //“发现一个函数”
		                    ++nHs;
		                --bHs;
		            }
		            //“函数统计：结束”
		            ++i;
		            bCode = 1;    //能执行到这里，说明该行存在代码
		        }
			}
		}
		data.nZs = nZs;
		data.nDm = nDm;
		data.nKh = nKh;
		data.nGg = nGg;
		data.nHs = nHs;
		data.printData();
		return data;
	}
}
