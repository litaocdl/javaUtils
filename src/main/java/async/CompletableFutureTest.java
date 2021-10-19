package async;
import java.util.concurrent.CompletableFuture;

public class CompletableFutureTest {

    public static void main(String[] args) throws InterruptedException {
    
        CompletableFuture<TaskStatus> future = CompletableFuture.supplyAsync(CompletableFutureTest::timeConsumerRun) ;
        future.thenApplyAsync(status -> {
            return CompletableFutureTest.handSuccess(status);
        }).thenApplyAsync(status -> {
           return CompletableFutureTest.handFailure(status);
        }).exceptionally(e -> {
            e.printStackTrace();
            return null ;
        }) ;

        Thread.sleep(3000);
    }

    public static TaskStatus handSuccess(TaskStatus status){
        if(status == TaskStatus.success){
            System.out.println(status + " I am success");
        }
        return status;
    }
    public static TaskStatus handFailure(TaskStatus status) {
        if (status == TaskStatus.failure) {
            System.out.println(status + " I am failure");
        }
        return status;
    }

    public static TaskStatus timeConsumerRun() {
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
            return TaskStatus.failure ;
        }
        double random = Math.random() ;
        if(random <= 0.3){
            return TaskStatus.failure ;
        }else if(random < 0.6){
            throw new RuntimeException("I am an exception thrown") ;
        }else{
            return TaskStatus.success ;
        }
    }
}


class TaskStatus {
    public static TaskStatus success = new TaskStatus("success") ;
    public static TaskStatus failure = new TaskStatus("failure") ;
    private String status ;
    public TaskStatus(String status){
        this.status = status ;
    }

    public String toString(){
        return this.status ;
    }
}