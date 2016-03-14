package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import algorithms.mazeGenerators.Maze3d;
import algorithms.Search.Solution;

public class MapToZipUtil implements Serializable {

	private static Map<Maze3d, Solution> solutionsMap = new HashMap();
	private static String mapPath = System.getProperty("user.dir") + "//" + "SolutionsMap.gzip";

	public static Map<Maze3d, Solution> getSolutionsMap() {
		if (solutionsMap != null) {
			return solutionsMap;
		}
		return solutionsMap;
	}

	public static void saveMapSolutions() {
		try {
			FileOutputStream fos = new FileOutputStream(mapPath);
			GZIPOutputStream gzos = new GZIPOutputStream(fos);
			ObjectOutputStream out = new ObjectOutputStream(gzos);
			out.writeObject(solutionsMap);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.getStackTrace();
		}
	}

	public static void readMapSolutions() throws ClassNotFoundException {
		try {
			GZIPInputStream gis = null;
			gis = new GZIPInputStream(new FileInputStream(mapPath));
			ObjectInputStream objectIn = new ObjectInputStream(gis);
			Map<Maze3d, Solution> map = (Map<Maze3d, Solution>) objectIn.readObject();
			solutionsMap = map;
			gis.close();
			objectIn.close();
		} catch (IOException e) {
			e.getStackTrace();
		}
	}
}
