
	import java.util.HashMap;
	import java.util.Map;
	import java.security.MessageDigest;
	import java.security.NoSuchAlgorithmException;

	public class LinkShortner {
	    private Map<String, String> shortToLongMap;
	    private Map<String, String> longToShortMap;

	    public LinkShortner() {
	        shortToLongMap = new HashMap<>();
	        longToShortMap = new HashMap<>();
	    }

	    public String shortenURL(String longURL) {
	        String shortURL = generateShortURL(longURL);
	        
	        if (shortToLongMap.containsKey(shortURL)) {
	            // Handle collision by generating a different short URL or other strategies
	            // This is a simplified example; you might want to implement a more robust solution
	            // such as appending a counter to the short URL.
	            return shortenURL(longURL + System.currentTimeMillis());
	        }

	        shortToLongMap.put(shortURL, longURL);
	        longToShortMap.put(longURL, shortURL);

	        return shortURL;
	    }

	    public String expandURL(String shortURL) {
	        return shortToLongMap.getOrDefault(shortURL, "URL not found");
	    }

	    private String generateShortURL(String longURL) {
	        try {
	            MessageDigest md = MessageDigest.getInstance("SHA-256");
	            byte[] hashBytes = md.digest(longURL.getBytes());

	            // Convert hashBytes to a short string (for simplicity, using the first 8 characters)
	            StringBuilder shortURL = new StringBuilder();
	            for (int i = 0; i < Math.min(8, hashBytes.length); i++) {
	                shortURL.append(Integer.toString((hashBytes[i] & 0xff) + 0x100, 16).substring(1));
	            }

	            return shortURL.toString();
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	            return "";
	        }
	    }

	    public static void main(String[] args) {
	        LinkShortner linkShortener = new LinkShortner();

	        // Example usage
	        String longURL = "https://www.example.com";
	        String shortURL = linkShortener.shortenURL(longURL);
	        System.out.println("Shortened URL: " + shortURL);

	        String expandedURL = linkShortener.expandURL(shortURL);
	        System.out.println("Expanded URL: " + expandedURL);
	    }
	}

