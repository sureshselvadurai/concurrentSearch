package hotelapp.Processer;

import hotelapp.Helper.Constants;
import hotelapp.Helper.Utils;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DirectoryTraversal {

    /** getFilesPath
     * Returns a list of file path in the input directory post filering the fileExtension
     * @param filePath Path to lookout for Files
     * @param fileExt Extension to filer
     */

    public List<Path> getFilesPath(String filePath, String fileExt) {
        List<Path> output = new ArrayList<>();
        Path pathObject = Paths.get(filePath);
        Boolean isFile = !Files.isDirectory(pathObject);

        if(isFile){
            output.add(pathObject);
            return output;
        }

        try (DirectoryStream<Path> pathsInDir = Files.newDirectoryStream(pathObject)) {
            for (Path path : pathsInDir) {
                if (Files.isDirectory(path)){
                    output.addAll(getFilesPath(path.toAbsolutePath().toString(),fileExt));
                }
                if (path.toString().endsWith(fileExt))
                    output.add(path);
            }
        } catch (IOException e) {
            Utils.throwError(Constants.ERR_404,Constants.ERR_404_MESSAGE);
        }
        return output;
    }

}
