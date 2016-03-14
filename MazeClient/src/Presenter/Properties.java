package Presenter;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
public class Properties {

	private int level;
	private int line;
	private int column;
	private int threadPoolNumber;
	private List<String> solvesAlgo;
	
	
	public List<String> getSolvesAlgo() {
		return solvesAlgo;
	}

	@XmlElement
	public void setSolvesAlgo(List<String> solvesAlgo) {
		this.solvesAlgo = solvesAlgo;
	}

	public int getLevel() {
		return level;
	}

	@XmlElement
	public void setLevel(int level) {
		this.level = level;
	}

	public int getLine() {
		return line;
	}

	@XmlElement
	public void setLine(int line) {
		this.line = line;
	}

	public int getColumn() {
		return column;
	}

	@XmlElement
	public void setColumn(int column) {
		this.column = column;
	}

	public int getThreadPoolNumber() {
		return threadPoolNumber;
	}

	@XmlElement
	public void setThreadPoolNumber(int threadPoolNumber) {
		this.threadPoolNumber = threadPoolNumber;
	}
}
