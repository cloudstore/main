package co.codewizards.cloudstore.core.oio.file;

import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.URI;


/**
 * @author Sebastian Schefczyk
 *
 */
public interface File extends OioService {

	/** Factory method, substitutes the constructor of {@link java.io.File}. */
	File createNewFile(String pathname);
	/** Factory method, substitutes the constructor of {@link java.io.File}. */
	File createNewFile(File parent, String child);
	/** Factory method, substitutes the constructor of {@link java.io.File}. */
	File createNewFile(String parent, String child);
	/** Factory method, substitutes the constructor of {@link java.io.File}. */
	File createNewFile(URI uri);
	/** Factory method, substitutes the constructor of {@link java.io.File}.
	 * <p>
	 * <b>Caution: </b><i>Use this creator only for 3rd-party interfaces!</i> */
	File createNewFile(java.io.File file);

	File getParentFile();

	String[] list();
	String[] list(FilenameFilter filenameFilter);
	File[] listFiles();
	File[] listFiles(FileFilter fileFilter);
	File[] listFiles(FilenameFilter fileFilter);

	File getAbsoluteFile();

	boolean isSymbolicLink();

	String readSymbolicLinkToPathString() throws IOException;
	boolean exists();
	boolean existsNoFollow();
	boolean canWrite();
	boolean canRead();
	boolean canExecute();
	boolean createNewFile() throws IOException;
	int compareTo(File otherFile);
	boolean delete();
	void deleteOnExit();
	long getFreeSpace();
	String getCanonicalPath() throws IOException;
	File getCanonicalFile() throws IOException;
	String getAbsolutePath();
	boolean isRegularFileFollowLinks();
	boolean isRegularFileNoFollowLinks();
	boolean mkdir();
	boolean isDirectory();
	boolean isDirectoryFileNoFollowSymLinks();
	boolean isDirectoryFollowSymLinks();
	long getUsableSpace();
	long length();
	boolean renameTo(File newFileName);
	void setLastModified(long lastModified);
	OutputStream createFileOutputStream() throws FileNotFoundException;
	OutputStream createFileOutputStream(boolean append) throws FileNotFoundException;
	InputStream createFileInputStream() throws FileNotFoundException;
	String getName();
	String createSymbolicLink(String targetPath) throws IOException;

	long lastModified();
	long getLastModifiedNoFollow();
	boolean isAbsolute();
	String getPath();
	boolean mkdirs();
	void copyToCopyAttributes(File toFile) throws IOException;
	void move(File toFile) throws IOException;
	URI toURI();
	RandomAccessFile createRandomAccessFile(String mode) throws FileNotFoundException;
	boolean isFile();
	void setLastModifiedNoFollow(long time);
	String relativize(File target);

	File createTempDirectory(String prefix) throws IOException;
	File createTempFile(final String prefix, final String suffix) throws IOException;
	File createTempFile(String prefix, String suffix, File dir) throws IOException;
	boolean setExecutable(boolean executable, boolean ownerOnly);
	/** <b>Caution:</b> <i>Only use this when forced by 3rd party interface!</i> */
	java.io.File getIoFile();

}