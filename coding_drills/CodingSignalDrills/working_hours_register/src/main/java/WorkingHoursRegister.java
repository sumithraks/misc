import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Iterator;

// Skeleton. Implement level by level; run `mvn test` after each level.
public class WorkingHoursRegister {

    public static class Employee {
        String id;
        String position;
        long hourlyRateCents;
        long time;

        public Employee(String id, String position, long hourlyRateCents, long time) {
            this.id=id;
            this.position=position;
            this.hourlyRateCents=hourlyRateCents;
            this.time=time;
        }
    }

    public static class Shift {
        long start;
        long end;
        long salary;
        boolean isInGrant=false;
        public Shift (long s,long e,long amt){
            this.start=s;
            this.end=e;
            this.salary=amt;
        }
    }

    public static class PendingPromotions {
        long start;
        String newPosition;
        long newRateCents;
        public PendingPromotions (long start,String newPosition, long newRateCents){
            this.start=start;
            this.newPosition=newPosition;
            this.newRateCents=newRateCents;
        }
    }

    public static class Grant {
        long start;
        long end;
        public Grant(long s,long e) {start=s;end=e;}
    }

    Map<String,Employee> employees = new HashMap<>();
    Map<String,Long> swipes = new HashMap<>();
    Map<String,Long> totalMinutes = new HashMap<>();
    Map<String,List<Shift>> shifts = new HashMap<>();
    Map<String,List<PendingPromotions>> promotions = new HashMap<>();
    List<Grant> grants = new ArrayList<>();

    // Level 1
    public void addEmployee(String id, String position, long hourlyRateCents, long time) throws Exception{

        if (id == null || id.isBlank()) {
            throw new Exception("Id should be present");
        }

        if (position == null || position.isBlank()) {
            throw new Exception("position should be present");
        }

        if (hourlyRateCents<=0) {
            throw new Exception("hourlyRateCents should be non-zero and positive");
        }

        if (time<=0) {
            throw new Exception("time added should be non-zero and positive");
        }

        if (employees.containsKey(id)) {
            throw new Exception("Employee already present");
        }
        Employee e = new Employee(id,position,hourlyRateCents,time);
        employees.put(id,e);
        totalMinutes.put(id,0L);
    }

    public void swipe(String id, long time) throws Exception{
        if (!employees.containsKey(id))
            throw new Exception("Employee not found");
        if (swipes.containsKey(id)) {
            // The employeed is already swiped in
            long swipeIn = swipes.get(id);
            long duration = time-swipeIn;
            if (duration<0) {
                throw new Exception("The swipe in record later than this record");
            }
            Shift s = new Shift(swipeIn,time,(duration*employees.get(id).hourlyRateCents)/60);
            Grant g = grants.stream().filter(grant -> grant.start <= s.start && grant.end >= s.end).findFirst().orElse(null);
            if (g != null)
                s.isInGrant=true;
            totalMinutes.put(id,totalMinutes.getOrDefault(id,0L)+duration);
            shifts.computeIfAbsent(id,k->new ArrayList<Shift>()).add(s);
            swipes.remove(id);
            return;
        } else {
            swipes.put(id,time);
            if (promotions.containsKey(id)) {
                List<PendingPromotions> pendingPromotions = promotions.get(id);
                Iterator<PendingPromotions> it = pendingPromotions.iterator();
                while (it.hasNext()) {
                    PendingPromotions p = it.next();
                    if (p.start <= time) {
                        Employee e=employees.get(id);
                        e.hourlyRateCents=p.newRateCents;
                        e.position=p.newPosition;
                        e.time=p.start;
                        it.remove();
                    }
                }
            }
        }
        
    }

    public long totalMinutes(String id) {
        return totalMinutes.getOrDefault(id,0L);
    }

    // Level 2
    public List<String> topN(int n) {
        if (n<=0)
            return new ArrayList<>();
        PriorityQueue<Map.Entry<String,Long>> topN = new PriorityQueue<>
        ((a, b) -> {
            int cmp = Long.compare(b.getValue(), a.getValue());   // minutes, highest first
            return cmp != 0 ? cmp : a.getKey().compareTo(b.getKey());  // then id, A before B
        });
        topN.addAll(totalMinutes.entrySet());
        int i=0;
        List<String> result = new ArrayList<>();
        while (!topN.isEmpty()) {
            Map.Entry<String, Long> entry = topN.poll();
            result.add(entry.getKey());
            i++;
            if (i >= n)
                break;
        }

        return result;
    }

    // Level 3
    public void promote(String id, String newPosition, long newRateCents, long time) throws Exception{
        if (id == null || id.isBlank()) {
            throw new Exception("Id should be present");
        }

        if (newPosition == null || newPosition.isBlank()) {
            throw new Exception("position should be present");
        }

        if (newRateCents<=0) {
            throw new Exception("hourlyRateCents should be non-zero and positive");
        }

        if (time<=0) {
            throw new Exception("time added should be non-zero and positive");
        }

        if (!employees.containsKey(id)) {
            throw new Exception("Employee not found");
        } 
        promotions.computeIfAbsent(id,k->new ArrayList<>()).add(new PendingPromotions(time,newPosition,newRateCents));
    }

    public String positionOf(String id) throws Exception{
        if (employees.containsKey(id)) {
            return employees.get(id).position;
        } else {
            throw new Exception("Employee not found");
        }
    }

    public long salaryBetween(String id, long from, long to) throws Exception{
        if (id == null || id.isBlank()) {
            throw new Exception("Id should be present");
        }
        if (!employees.containsKey(id))
            throw new Exception("Employee not found");

        if (shifts.containsKey(id)) {
            List<Shift> emp_shifts = shifts.get(id);
            long total = 0;
            for (Shift s: emp_shifts) {
                if (s.start < from || s.end > to) 
                    continue;
                
                total+=s.salary;
                if (s.isInGrant)
                    total+=s.salary;
            }
            return total;
        }
        return 0;
    }

    // Level 4
    public void addGrant(long start, long end) throws Exception {
        if (start <= 0 || end <= 0 || end < start) {
            throw new Exception("Invalid duration start < end and neither can be zero or negative");
        }
        Grant g = new Grant(start,end);
        grants.add(g);
    }

    public long totalDoublePay() {
        long total=0;
        for (Map.Entry<String,List<Shift>> e: shifts.entrySet()) {
            List<Shift> shifts = e.getValue();
            for (Shift s: shifts) {
                if (s.isInGrant)
                    total+=s.salary;
            }
        }
        return total;
    }
}
