package com.bridgelabz.employeepayroll;

import java.io.IOException;
import java.nio.*;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.StandardWatchEventKinds;
import java.util.HashMap;
import java.util.Map;

public class Java8WatchService {
	private final WatchService watcher;
	private final Map<WatchKey, Path> dirWatchers;

	public Java8WatchService(Path dir) throws IOException {
		this.watcher=FileSystems.getDefault().newWatchService();
		this.dirWatchers=new HashMap<WatchKey, Path>();
		scanAndRegisterDirectories(dir);
	}
	
	private void registerDirWatchers(Path dir) throws IOException{
		WatchKey key=dir.register(watcher,StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
		dirWatchers.put(key, dir);
	}
	private void scanAndRegisterDirectories(final Path start) throws IOException {
		Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				registerDirWatchers(dir);
				return FileVisitResult.CONTINUE;
			}
		});
	}

}
