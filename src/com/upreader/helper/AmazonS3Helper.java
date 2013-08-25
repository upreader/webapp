package com.upreader.helper;

import com.upreader.UpreaderConstants;

/**
 * Created
 * User: Razvan.Ionescu
 * Date: 8/21/13
 */
public class AmazonS3Helper {

    public static String trimPath(final String parent, final String child) {
        if (!child.startsWith(parent)) {
            throw new IllegalArgumentException("Invalid child '" + child
                    + "' for parent '" + parent + "'");
        }
        final int parentLen = parent.length();
        return child.substring(parentLen);
    }

    public static String getExt(final String parent, final String child) {
        if (!child.startsWith(parent)) {
            throw new IllegalArgumentException("Invalid child '" + child
                    + "' for parent '" + parent + "'");
        }
        final int parentLen = parent.length();
        String fileName = child.substring(parentLen);
        return fileName.substring(fileName.indexOf('.')+1);
    }

    public static boolean isImage(final String parent, final String child){
        String ext = getExt(parent, child);

        return UpreaderConstants.EXT_IMG.contains(ext);
    }

    public static boolean isImmediateDescendant(final String parent, final String child) {
        if (!child.startsWith(parent)) {
           return false;
        }
        final int parentLen = parent.length();
        final String childWithoutParent = child.substring(parentLen);
        if (childWithoutParent.contains("/") || childWithoutParent.equals("")) {
            return false;
        }
        return true;
    }

}
