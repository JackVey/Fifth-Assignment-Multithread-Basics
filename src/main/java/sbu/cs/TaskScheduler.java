package sbu.cs;

import java.util.ArrayList;

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
            try {
                Thread.sleep(processingTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static ArrayList<String> doTasks(ArrayList<Task> tasks)
    {
        ArrayList<String> finishedTasks;
        try {
            finishedTasks = new ArrayList<>();
            ArrayList<Task> tempTasks = new ArrayList<>(sortTaskArrayList(tasks));
            for (Task tempTask : tempTasks) {
                Thread newThread = new Thread(tempTask);
                newThread.start();
                newThread.join();
                finishedTasks.add(tempTask.taskName);
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

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
