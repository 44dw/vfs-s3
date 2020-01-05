#!/usr/bin/env groovy

@Grab(group='com.github.abashev', module='vfs-s3', version='4.1.0-SNAPSHOT')
import org.apache.commons.vfs2.*
import java.text.SimpleDateFormat

if (args.length != 1) {
    println 'Use: cloud-list <s3 url>'
    println 'Url example - s3://s3.eu-central-1.amazonaws.com/s3-tests-2'

    return
}

FileObject[] files = VFS.getManager().resolveFile(args[0]).findFiles(Selectors.EXCLUDE_SELF);

if ((files != null) && files.length > 0) {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    for (FileObject file : files) {
        String lastModDate = df.format(new Date(file.getContent().getLastModifiedTime()));
        String fullPath = file.getName().getURI();

        if (file.isFile()) {
            long size = file.getContent().getSize();

            System.out.println(String.format("%-20s%-10s%s", lastModDate, size, fullPath));
        } else {
            System.out.println(String.format("%-20s  -dir-   %s", lastModDate, fullPath));
        }
    }
}

