import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class TicketQueue {

    public static class Ticket {
        String title;
        int priority; 
        long timestamp;
        Integer id;
        String agent;
        long expiry;
        boolean isAssigned = false;
        boolean isCompleted = false;

        public Ticket(int id,String title, int priority, long timestamp) {
            this.title=title;
            this.priority=priority;
            this.timestamp=timestamp;
            this.id=id;
        }

        public int priority() {return priority;}
        public long timestamp() {return timestamp;}
        public int id() {return id;}
        public String title() {return title;}

        public void assign(String agent,long now, long durationMs) {
            this.agent=agent;
            this.expiry=now+durationMs;
            this.isAssigned=true;
        }

        public void uassign() {
            this.agent=null;
            this.expiry=0;
            this.isAssigned=false;
        }

        public void complete(long now) {
            isCompleted=true;
        }

        public long expiry() {return expiry;}
        public boolean isAssigned() {return isAssigned;}

        public boolean isCompleted() {return isCompleted;}
    }


    private void clearAssignment(Ticket t) {
        if (t.agent != null && !t.agent.isBlank())
            assignments.get(t.agent).remove(t);
        t.uassign();
        
    }

    private void setAsssignet(Ticket t,String agent,long now, long durationMs) {
        clearAssignment(t);
        t.assign(agent, now, durationMs);
        assignments.computeIfAbsent(agent, s->new HashSet()).add(t);
        
    }


    Map<Integer,Ticket> tickets = new HashMap<>();
    Map<String,Set<Ticket>> assignments = new HashMap<>();

    Comparator<Ticket> searchRes = Comparator
        .comparingInt(Ticket::priority).reversed()
        .thenComparingLong(Ticket::timestamp)
        .thenComparingInt(Ticket::id);
    // Level 1
    public void create(int id, String title, int priority, long timestamp){
        if (tickets.containsKey(id)) {
            return;
        }
        Ticket t = new Ticket(id, title, priority, timestamp);
        tickets.put(id,t);
    }

    public void updateTitle(int id, String title)  {
        if (!tickets.containsKey(id)) {
            return;
        }
        tickets.get(id).title=title;
    }

    public String getTitle(int id)  {
        if (!tickets.containsKey(id)) {
            return "";
        }
        return tickets.get(id).title;
    }

    // Level 2
    public List<Integer> search(String keyword) {
        List<Ticket> ids = new ArrayList<>();
        for (Ticket d:tickets.values()) {
            if (d.title.contains(keyword)) 
                ids.add(d);
        }
        
        return ids.stream()
                .sorted(searchRes)
                .map(Ticket::id).toList();
    }

    public List<Integer> topOpen(int n) {
        if (n<=0) {
            return new ArrayList<>();
        }
        return tickets.values().stream().sorted(searchRes).map(Ticket::id).limit(n).toList();
    }

    // Level 3
    public boolean assign(int ticketId, String agent, long now, long durationMs) {
        if (!tickets.containsKey(ticketId))
            return false;
        Ticket t = tickets.get(ticketId);
        if (t.isAssigned 
            && assignments.containsKey(t.agent) 
            && assignments.get(t.agent).contains(t)
            && t.expiry > now)
            return false;

        String prevAgent = t.agent;
        
        if (!assignments.containsKey(agent)) {
            setAsssignet(t,agent,now,durationMs);
            return true;
        } 
        Set<Ticket> ticketSet = assignments.get(agent);

        Iterator<Ticket> it = ticketSet.iterator();
        while (it.hasNext()) {
            Ticket ticket = it.next();
            if (now >= ticket.expiry) {
                it.remove();
            }
        }
        if (ticketSet.size()>=3) 
            return false;
        setAsssignet(t,agent, now, durationMs);
        return true;
    }

    // Level 4
    public boolean complete(int ticketId, long now) {
        if (!tickets.containsKey(ticketId))
            return false;
        Ticket t = tickets.get(ticketId);
        if (t.isCompleted() || t.isAssigned() ==false) 
            return false;
        t.complete(now);
        assignments.get(t.agent).remove(t);
        return true;
    }

    public List<Integer> overdueReport(long now) {
        return tickets.values().stream()
        .filter(t->t.isAssigned() && !t.isCompleted() && t.expiry() <= now)
        .map(Ticket::id).sorted().toList();
    }
}
