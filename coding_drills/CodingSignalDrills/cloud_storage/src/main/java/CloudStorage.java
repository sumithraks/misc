import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Comparator;

public class CloudStorage {

    public static class File {
        private String name;
        private long size;
        private String owner;

        public String name() {
            return name;
        }

        public long size() {
            return size;
        }

        public String owner() {
            return owner;
        }

         public void changeOwner(String o) {
            this.owner=o;
        }


        public File(String n,long s,String o) {
            this.name = n;
            this.size = s;
            this.owner = o;
        }
    }

    public static class User {
        String userId;
        long capacity;
        Set<File> files = new HashSet<>();
        long remainingCapacity;

        public User(String userId,long capacity) {
            this.userId=userId;
            this.capacity=capacity;
            this.remainingCapacity=capacity;
        }

        public String userId() {
            return userId;
        }

        public long capacity() {
            return capacity;
        }

        public Set<File> files() {
            return files;
        }


        public long remainingCapacity() {
            return remainingCapacity;
        }

        public boolean addFile(File f) {
            if (remainingCapacity < f.size() )
                return false;

            files.add(f);
            remainingCapacity-=f.size();
            return true;
        }

        public void deleteFile(File f) {
            if (files.contains(f)) {
                remainingCapacity+=f.size();
                files.remove(f);
                return;
            }
            return;
        }

    }

    // Look up by Files
    Map<String, File> files = new HashMap<>();

    // Look up user
    Map<String,User> users = new HashMap<>();

    // Level 1
    public boolean addFile(String name, long size) {
        if (size < 0)
            return false;
        if (files.containsKey(name))
            return false;
        File f = new File(name,size,null);
        files.put(name,f);
        return true;
    }

    public long getFileSize(String name) {
        if (files.containsKey(name))
            return files.get(name).size();
        return -1;
    }

    public boolean deleteFile(String name) {
        if (!files.containsKey(name))
            return false;
        String owner = files.get(name).owner;
        if (owner != null && !owner.isBlank())
        {
            if (users.containsKey(owner))
                users.get(owner).deleteFile(files.get(name));
        }
        files.remove(name);
        return true;
    }

    // Level 2
    public List<String> getLargestFiles(String prefix, int n) {
        if (n<0)
            return new ArrayList<>();
        Comparator<File> fileComparator = 
            Comparator.comparingLong(File::size).reversed().thenComparing(File::name);
        return files.values().stream()
        .filter(f -> f.name().startsWith(prefix)).sorted(fileComparator)
        .map(File::name).limit(n).toList();
    }

    // Level 3
    public boolean addUser(String userId, long capacity) {
        if (users.containsKey(userId))
            return false;
        User u = new User(userId,capacity);
        users.put(userId,u);
        return true;
    }

    public boolean addFileBy(String userId, String name, long size) {
        if (size < 0)
            return false;
        if (!users.containsKey(userId) || files.containsKey(name))
            return false;
        
        File f = new  File(name,size,userId);
        if (users.get(userId).addFile(f)) {
            files.put(name,f);
            return true;
        }
        return false;
    }

    public long remainingCapacity(String userId) {
        if (!users.containsKey(userId) )
            return -1;
        return users.get(userId).remainingCapacity();
    }

    // Level 4
    public boolean copyFile(String source, String dest) {
        if (!files.containsKey(source) || files.containsKey(dest))
            return false;
        File f = files.get(source);
        User u = users.get(f.owner);
        if (u!=null && u.remainingCapacity() < f.size())
            return false;
        File copy = new File(dest,f.size,f.owner);
        if (u!=null) u.addFile(copy);
        files.put(dest,copy);
        return true;
    }

    public boolean mergeUsers(String from, String to) {
        if (from.equals(to) || !users.containsKey(from) || !users.containsKey(to))
            return false;
        long requiredCapacity = users.get(from).capacity()-users.get(from).remainingCapacity();
        if (requiredCapacity>users.get(to).remainingCapacity())
            return false;
        User receiver = users.get(to);
        for (File f: users.get(from).files()) {
            f.changeOwner(to);
            receiver.addFile(f);
        }
        users.remove(from);
        return true;
    }

}
