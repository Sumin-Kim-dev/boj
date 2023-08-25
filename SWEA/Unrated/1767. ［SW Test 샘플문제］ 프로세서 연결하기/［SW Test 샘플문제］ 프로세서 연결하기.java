import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Solution {
	
	static int n;
	static int[][] map;
	static List<int[]> cores;
	static int nCore;
	
	static int maxConnected;
	static int minLength;
	
	static int[] dr = {-1, 0, 1, 0};
	static int[] dc = {0, 1, 0, -1};

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int tc = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		for (int t = 1; t <= tc; t++) {
			n = Integer.parseInt(br.readLine());
			map = new int[n][n];
			cores = new ArrayList<>();
			nCore = 0;
			maxConnected = -1;
			minLength = Integer.MAX_VALUE;
			for (int i = 0; i < n; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				for (int j = 0; j < n; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
					if(map[i][j] == 1) {
						cores.add(new int[] {i, j});
						nCore++;
					}
				}
			}
			backtracking(0, 0, 0);
			sb.append('#').append(t).append(' ').append(minLength).append('\n');
		}
		System.out.println(sb);
	}

	private static void backtracking(int cnt, int connected, int length) {
		if (cnt == nCore) {
			if (connected > maxConnected || connected == maxConnected && length < minLength) {
				maxConnected = connected;
				minLength = length;
			}
			return;
		}
		int[] core = cores.get(cnt);
		// 연결 안함
		backtracking(cnt + 1, connected, length);
		// 연결
		for (int d = 0; d < 4; d++) {
			if (canConnect(cnt, d)) {
				connect(cnt, d);
				backtracking(cnt + 1, connected + 1, length + dist(cnt, d));
				disconnect(cnt, d);
			}
		}
	}

	private static int dist(int cnt, int d) {
		int[] core = cores.get(cnt);
		int r = core[0];
		int c = core[1];
		if (d == 0) return r;
		if (d == 1) return n - 1 - c;
		if (d == 2) return n - 1 - r;
		if (d == 3) return c;
		return 0;
	}

	private static boolean isIn(int r, int c) {
		return r >= 0 && r < n && c >= 0 && c < n;
	}

	private static boolean canConnect(int cnt, int d) {
		int[] core = cores.get(cnt);
		int r = core[0] + dr[d];
		int c = core[1] + dc[d];
		while (isIn(r, c)) {
			if (map[r][c] > 0) return false;
			r += dr[d];
			c += dc[d];
		}
		return true;
	}
	
	private static void connect(int cnt, int d) {
		int[] core = cores.get(cnt);
		int r = core[0] + dr[d];
		int c = core[1] + dc[d];
		while (isIn(r, c)) {
			map[r][c] = cnt + 2;
			r += dr[d];
			c += dc[d];
		}
	}
	
	private static void disconnect(int cnt, int d) {
		int[] core = cores.get(cnt);
		int r = core[0] + dr[d];
		int c = core[1] + dc[d];
		while (isIn(r, c)) {
			if (map[r][c] == cnt + 2) {
				map[r][c] = 0;
			}
			r += dr[d];
			c += dc[d];
		}
	}
}