package io.ganguo.demo.entity;

/**
 * Created by Roger on 7/28/16.
 */
public class UploadToken extends UploadTokenEntity {

    public UploadToken(String domain, String token) {
        super(domain, token);
    }

    public static UploadToken getErrorToken() {
        String domain = "7xsflv.com1.z0.glb.clouddn.com";
        String token = "Kq5o85AKDTIe0fBUZavb4vVxJ4BC-HCH092zROqf:O6c_SBG5OJMlMTrN8uwgBMMvsfE\\u003d:eyJlbmRVc2VyIjoiV3U4cU5PeFBEcU82M01rdyIsImNhbGxiYWNrVXJsIjoiaHR0cDpcL1wvMTIxLjIwMS4yOC4xNTlcL2FwaVwvaGVscGVyXC9xaW5pdSIsImNhbGxiYWNrQm9keVR5cGUiOiJhcHBsaWNhdGlvblwvanNvbiIsImNhbGxiYWNrQm9keSI6IntcInV1aWRcIjpcIiQodXVpZClcIixcImhhc2hcIjpcIiQoZXRhZylcIixcImJ1Y2tldFwiOlwiJChidWNrZXQpXCIsXCJrZXlcIjpcIiQoa2V5KVwiLFwibWltZV90eXBlXCI6XCIkKG1pbWVUeXBlKVwiLFwiZm5hbWVcIjpcIiQoZm5hbWUpXCIsXCJmc2l6ZVwiOlwiJChmc2l6ZSlcIixcImVuZF91c2VyXCI6XCIkKGVuZFVzZXIpXCIsXCJ1c2VyX2FnZW50XCI6XCJhcHBcXFwvMS4wIChhbmRyb2lkOyA2LjA7IDIzKVwifSIsInNjb3BlIjoidW5pY29ybiIsImRlYWRsaW5lIjoxNDY5Njc0MzI1fQ\\u003d\\u003d";
        return new UploadToken(domain, token);
    }

}
