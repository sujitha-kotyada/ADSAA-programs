//Implement Job Sequencing with deadlines using Greedy strategy.
import java.util.*;

class Job {
    int id, deadline, profit;

    Job(int id, int deadline, int profit) {
        this.id = id;
        this.deadline = deadline;
        this.profit = profit;
    }
}

public class JobSequencing {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter no. of Jobs: ");
        int n = sc.nextInt();
        Job[] jobs = new Job[n];
        System.out.println("Enter job details (id deadline profit): ");
        for (int i = 0; i < n; i++) {
            int id = sc.nextInt();
            int deadline = sc.nextInt();
            int profit = sc.nextInt();
            jobs[i] = new Job(id, deadline, profit);
        }

        Arrays.sort(jobs, (a, b) -> b.profit - a.profit);
        int maxDeadline = 0;
        for (Job job : jobs) {
            if (job.deadline > maxDeadline)
                maxDeadline = job.deadline;
        }

        int[] slot = new int[maxDeadline];
        Arrays.fill(slot, -1);
        int totalProfit = 0;
        for (Job job : jobs) {
            for (int j = job.deadline - 1; j >= 0; j--) {
                if (slot[j] == -1) {
                    slot[j] = job.id;
                    totalProfit += job.profit;
                    break;
                }
            }
        }

        System.out.println("Selected Jobs: ");
        for (int i = 0; i < maxDeadline; i++) {
            if (slot[i] != -1)
                System.out.print("J" + slot[i] + " ");
        }
        System.out.println("\nTotal Profit: " + totalProfit);
        sc.close();
    }
}
