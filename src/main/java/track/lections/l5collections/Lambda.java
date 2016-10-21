package track.lections.l5collections;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class Lambda {

    public static void main(String[] args) throws Exception {
        renderPage("");
    }

    public static void renderPage(String str) throws Exception {
        List<ImageInfo> strs = Arrays.asList(new ImageInfo("url1"), new ImageInfo("url2"));

        strs.stream()
                .forEach(imageInfo ->
                CompletableFuture
                        .supplyAsync(imageInfo::download)
                        .thenAccept(Lambda::renderImage));


        ExecutorService service = Executors.newFixedThreadPool(2);
        FutureTask<String> task = new FutureTask<>(() -> {
            try {
                Long id = Thread.currentThread().getId();
                System.out.println(id + ":" + "Waiting for result...");
                TimeUnit.SECONDS.sleep(2);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return "test";
        });

        service.submit(task);

        String result = task.get();
        System.out.println(">" + result);

        service.shutdown();


    }

    static void renderImage(ImageData image) {
        System.out.println(Thread.currentThread().getName() + ": ==");
        System.out.println("RENDER: " + image.data);
    }

    static class ImageInfo {
        private String url;

        public ImageInfo(String url) {
            this.url = url;
        }

        public ImageData download() {
            System.out.println(Thread.currentThread().getName() + ": ==");
            System.out.println("Loading image data - " + url);
            System.out.println("Loaded");
            return new ImageData(url + "-x");
        }
    }

    static class ImageData {
        String data;

        public ImageData(String data) {
            this.data = data;
        }
    }

}
