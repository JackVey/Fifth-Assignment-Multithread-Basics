package sbu.cs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TaskScheduler
{
    public static class Task implements Runnable
    {
        /*
            ------------------------- You don't need to modify this part of the code -------------------------
         */
        String taskName;
        int processingTime;

        public Task(String taskName, int processingTime) {
            this.taskName = taskName;
            this.processingTime = processingTime;
        }
        /*
            ------------------------- You don't need to modify this part of the code -------------------------
         */

        @Override
        public void run() {
            /*
            TODO
                Simulate utilizing CPU by sleeping the thread for the specified processingTime
             */
        }
    }

    public static ArrayList<String> doTasks(ArrayList<Task> tasks)
    {
        ArrayList<String> finishedTasks = new ArrayList<>();
        tasks = sortTaskArrayList(tasks);

        /*
        TODO
            Create a thread for each given task, And then start them based on which task has the highest priority
            (highest priority belongs to the tasks that take more time to be completed).
            You have to wait for each task to get done and then start the next task.
            Don't forget to add each task's name to the finishedTasks after it's completely finished.
         */

        return finishedTasks;
    }

    public static ArrayList<Task> sortTaskArrayList(ArrayList<Task> task){
        ArrayList<Task> tempTask = new ArrayList<>(task);
        boolean swapped;
        for (int i = 0; i < tempTask.size() - 1; i++) {
            swapped = false;
            for (int j = 0; j < tempTask.size() - i - 1; j++) {
                if (tempTask.get(j).processingTime < tempTask.get(j + 1).processingTime){
                    Task temp = tempTask.get(j);
                    tempTask.set(j, tempTask.get(j + 1));
                    tempTask.set(j + 1, temp);
                    swapped = true;
                }
            }
            if (!swapped){
                break;
            }
        }
        return tempTask;
    }
    public static void main(String[] args) {

    }
}
