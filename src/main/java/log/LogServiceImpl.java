package log;

public class LogServiceImpl implements logService {
    @Override
    public void logInfo(String message) {
        System.out.println(message);
    }

    @Override
    public void logError(String message) {
        System.out.println(message);
    }
}