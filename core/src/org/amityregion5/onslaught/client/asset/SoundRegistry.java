package org.amityregion5.onslaught.client.asset;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.amityregion5.onslaught.Onslaught;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

public class SoundRegistry {
	private static HashMap<String, Sound> sounds = new HashMap<String, Sound>();

	public static boolean tryRegister(String path) {
		if (sounds.containsKey(path)) { return true; }
		FileHandle handle = Onslaught.instance.gameData.child(path);
		if (handle.exists() && handle.extension().equals("wav") || handle.extension().equals("mp3") || handle.extension().equals("ogg")) {
			Onslaught.log("Sound Registry: registering: " + path);
			register(path, handle);
			return true;
		}
		return false;
	}

	public static void register(String path, FileHandle file) {
		Sound sound = Gdx.audio.newSound(file);
		sounds.put(path, sound);
	}

	public static List<Sound> getSoundsFor(String str) {
		List<Sound> t = sounds.keySet().stream().sequential().filter((s) -> s.matches(regexify(str))).map((k) -> sounds.get(k)).collect(Collectors.toList());
		return t;
	}

	public static List<String> getSoundNamesFor(String str) {
		List<String> t = sounds.keySet().stream().sequential().filter((s) -> s.matches(regexify(str))).collect(Collectors.toList());
		return t;
	}

	private static String regexify(String str) {
		if (str == null) { return ""; }
		String finalString = "";

		String[] split = str.split(Pattern.quote("**"));
		for (int i = 0; i < split.length; i++) {
			String[] split2 = split[i].split(Pattern.quote("*"));
			for (int i2 = 0; i2 < split2.length; i2++) {
				String[] split3 = split2[i2].split(Pattern.quote("?"));
				for (int i3 = 0; i3 < split3.length; i3++) {
					finalString += split3[i3] + (i3 == split3.length - 1 ? "" : "[^/]?");
				}
				finalString += (split2[i2].endsWith("?") ? "[^/]?" : "");
				finalString += (i2 == split2.length - 1 ? "" : "[^/]*");
			}
			finalString += (split[i].endsWith("*") ? "[^/]*" : "");
			finalString += (i == split.length - 1 ? "" : ".*");
		}
		finalString += (str.endsWith("**") ? ".*" : "");

		return finalString;
		// return str.replace("*", "[^/]*").replace("?", "[^/]?");
	}

	public static void dispose() {
		for (Sound a : sounds.values()) {
			a.dispose();
		}
		sounds.clear();
		sounds = null;
	}
}
