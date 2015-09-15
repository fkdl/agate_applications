/*
 * Stub for class sapphire.appexamples.minnietwitter.app.User
 * Generated by Sapphire Compiler (sc).
 */
package sapphire.appexamples.minnietwitter.app.stubs;


public final class User_Stub extends sapphire.appexamples.minnietwitter.app.User implements sapphire.common.AppObjectStub {

    sapphire.policy.SapphirePolicy.SapphireClientPolicy $__client = null;
    boolean $__directInvocation = false;

    public User_Stub (sapphire.appexamples.minnietwitter.app.UserInfo $param_UserInfo_1, sapphire.appexamples.minnietwitter.app.TagManager $param_TagManager_2) {
        super($param_UserInfo_1, $param_TagManager_2);
    }


    public void $__initialize(sapphire.policy.SapphirePolicy.SapphireClientPolicy client) {
        $__client = client;
    }

    public void $__initialize(boolean directInvocation) {
        $__directInvocation = directInvocation;
    }

    public Object $__clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public sapphire.policy.SapphirePolicy.SapphireClientPolicy $__getReference() {
        return $__client;
    }



    // Implementation of uploadPhoto(byte[])
    public int uploadPhoto(byte[] $param_arrayOf_byte_1) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.uploadPhoto( $param_arrayOf_byte_1);
            else {
                Object[] $__params = new Object[1];
                String $__method = "public int sapphire.appexamples.minnietwitter.app.User.uploadPhoto(byte[])";
                $__params[0] = $param_arrayOf_byte_1;
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.lang.Integer) $__result).intValue();
    }

    // Implementation of tweet(String, String, int)
    public sapphire.appexamples.minnietwitter.app.Tweet tweet(java.lang.String $param_String_1, java.lang.String $param_String_2, int $param_int_3) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.tweet( $param_String_1,  $param_String_2,  $param_int_3);
            else {
                Object[] $__params = new Object[3];
                String $__method = "public sapphire.appexamples.minnietwitter.app.Tweet sapphire.appexamples.minnietwitter.app.User.tweet(java.lang.String,java.lang.String,int)";
                $__params[0] = $param_String_1;
                $__params[1] = $param_String_2;
                $__params[2] = $param_int_3;
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((sapphire.appexamples.minnietwitter.app.Tweet) $__result);
    }

    // Implementation of tweet(String, String)
    public sapphire.appexamples.minnietwitter.app.Tweet tweet(java.lang.String $param_String_1, java.lang.String $param_String_2) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.tweet( $param_String_1,  $param_String_2);
            else {
                Object[] $__params = new Object[2];
                String $__method = "public sapphire.appexamples.minnietwitter.app.Tweet sapphire.appexamples.minnietwitter.app.User.tweet(java.lang.String,java.lang.String)";
                $__params[0] = $param_String_1;
                $__params[1] = $param_String_2;
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((sapphire.appexamples.minnietwitter.app.Tweet) $__result);
    }

    // Implementation of retweetedBy(User, int)
    public sapphire.appexamples.minnietwitter.app.Tweet retweetedBy(sapphire.appexamples.minnietwitter.app.User $param_User_1, int $param_int_2) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.retweetedBy( $param_User_1,  $param_int_2);
            else {
                Object[] $__params = new Object[2];
                String $__method = "public sapphire.appexamples.minnietwitter.app.Tweet sapphire.appexamples.minnietwitter.app.User.retweetedBy(sapphire.appexamples.minnietwitter.app.User,int)";
                $__params[0] = $param_User_1;
                $__params[1] = $param_int_2;
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((sapphire.appexamples.minnietwitter.app.Tweet) $__result);
    }

    // Implementation of retweet(String, int)
    public sapphire.appexamples.minnietwitter.app.Tweet retweet(java.lang.String $param_String_1, int $param_int_2) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.retweet( $param_String_1,  $param_int_2);
            else {
                Object[] $__params = new Object[2];
                String $__method = "public sapphire.appexamples.minnietwitter.app.Tweet sapphire.appexamples.minnietwitter.app.User.retweet(java.lang.String,int)";
                $__params[0] = $param_String_1;
                $__params[1] = $param_int_2;
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((sapphire.appexamples.minnietwitter.app.Tweet) $__result);
    }

    // Implementation of removeFollowing(User)
    public void removeFollowing(sapphire.appexamples.minnietwitter.app.User $param_User_1) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.removeFollowing( $param_User_1);
            else {
                Object[] $__params = new Object[1];
                String $__method = "public void sapphire.appexamples.minnietwitter.app.User.removeFollowing(sapphire.appexamples.minnietwitter.app.User)";
                $__params[0] = $param_User_1;
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of removeFollower(User)
    public void removeFollower(sapphire.appexamples.minnietwitter.app.User $param_User_1) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.removeFollower( $param_User_1);
            else {
                Object[] $__params = new Object[1];
                String $__method = "public void sapphire.appexamples.minnietwitter.app.User.removeFollower(sapphire.appexamples.minnietwitter.app.User)";
                $__params[0] = $param_User_1;
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of mentionedBy(Tweet)
    public void mentionedBy(sapphire.appexamples.minnietwitter.app.Tweet $param_Tweet_1) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.mentionedBy( $param_Tweet_1);
            else {
                Object[] $__params = new Object[1];
                String $__method = "public void sapphire.appexamples.minnietwitter.app.User.mentionedBy(sapphire.appexamples.minnietwitter.app.Tweet)";
                $__params[0] = $param_Tweet_1;
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of initialize(User, UserManager)
    public void initialize(sapphire.appexamples.minnietwitter.app.User $param_User_1, sapphire.appexamples.minnietwitter.app.UserManager $param_UserManager_2) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.initialize( $param_User_1,  $param_UserManager_2);
            else {
                Object[] $__params = new Object[2];
                String $__method = "public void sapphire.appexamples.minnietwitter.app.User.initialize(sapphire.appexamples.minnietwitter.app.User,sapphire.appexamples.minnietwitter.app.UserManager)";
                $__params[0] = $param_User_1;
                $__params[1] = $param_UserManager_2;
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of homeTweet(Tweet)
    public void homeTweet(sapphire.appexamples.minnietwitter.app.Tweet $param_Tweet_1) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.homeTweet( $param_Tweet_1);
            else {
                Object[] $__params = new Object[1];
                String $__method = "public void sapphire.appexamples.minnietwitter.app.User.homeTweet(sapphire.appexamples.minnietwitter.app.Tweet)";
                $__params[0] = $param_Tweet_1;
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of getUserTimeline(int, int)
    public java.util.List getUserTimeline(int $param_int_1, int $param_int_2) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.getUserTimeline( $param_int_1,  $param_int_2);
            else {
                Object[] $__params = new Object[2];
                String $__method = "public java.util.List<sapphire.appexamples.minnietwitter.app.Tweet> sapphire.appexamples.minnietwitter.app.User.getUserTimeline(int,int)";
                $__params[0] = $param_int_1;
                $__params[1] = $param_int_2;
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.util.List) $__result);
    }

    // Implementation of getUserInfo()
    public sapphire.appexamples.minnietwitter.app.UserInfo getUserInfo() {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.getUserInfo();
            else {
                Object[] $__params = null;
                String $__method = "public sapphire.appexamples.minnietwitter.app.UserInfo sapphire.appexamples.minnietwitter.app.User.getUserInfo()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((sapphire.appexamples.minnietwitter.app.UserInfo) $__result);
    }

    // Implementation of getPhoto(int)
    public byte[] getPhoto(int $param_int_1)
            throws java.lang.Exception {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.getPhoto( $param_int_1);
            else {
                Object[] $__params = new Object[1];
                String $__method = "public byte[] sapphire.appexamples.minnietwitter.app.User.getPhoto(int) throws java.lang.Exception";
                $__params[0] = $param_int_1;
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((byte[]) $__result);
    }

    // Implementation of getMentionsTimeline(int, int)
    public java.util.List getMentionsTimeline(int $param_int_1, int $param_int_2) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.getMentionsTimeline( $param_int_1,  $param_int_2);
            else {
                Object[] $__params = new Object[2];
                String $__method = "public java.util.List<sapphire.appexamples.minnietwitter.app.Tweet> sapphire.appexamples.minnietwitter.app.User.getMentionsTimeline(int,int)";
                $__params[0] = $param_int_1;
                $__params[1] = $param_int_2;
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.util.List) $__result);
    }

    // Implementation of getHomeTimeline(int, int)
    public java.util.List getHomeTimeline(int $param_int_1, int $param_int_2) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.getHomeTimeline( $param_int_1,  $param_int_2);
            else {
                Object[] $__params = new Object[2];
                String $__method = "public java.util.List<sapphire.appexamples.minnietwitter.app.Tweet> sapphire.appexamples.minnietwitter.app.User.getHomeTimeline(int,int)";
                $__params[0] = $param_int_1;
                $__params[1] = $param_int_2;
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.util.List) $__result);
    }

    // Implementation of getFollowing(int, int)
    public java.util.List getFollowing(int $param_int_1, int $param_int_2) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.getFollowing( $param_int_1,  $param_int_2);
            else {
                Object[] $__params = new Object[2];
                String $__method = "public java.util.List<sapphire.appexamples.minnietwitter.app.User> sapphire.appexamples.minnietwitter.app.User.getFollowing(int,int)";
                $__params[0] = $param_int_1;
                $__params[1] = $param_int_2;
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.util.List) $__result);
    }

    // Implementation of getFollowing()
    public java.util.List getFollowing() {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.getFollowing();
            else {
                Object[] $__params = null;
                String $__method = "public java.util.List<sapphire.appexamples.minnietwitter.app.User> sapphire.appexamples.minnietwitter.app.User.getFollowing()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.util.List) $__result);
    }

    // Implementation of getFollowers(int, int)
    public java.util.List getFollowers(int $param_int_1, int $param_int_2) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.getFollowers( $param_int_1,  $param_int_2);
            else {
                Object[] $__params = new Object[2];
                String $__method = "public java.util.List<sapphire.appexamples.minnietwitter.app.User> sapphire.appexamples.minnietwitter.app.User.getFollowers(int,int)";
                $__params[0] = $param_int_1;
                $__params[1] = $param_int_2;
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.util.List) $__result);
    }

    // Implementation of getFollowers()
    public java.util.List getFollowers() {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.getFollowers();
            else {
                Object[] $__params = null;
                String $__method = "public java.util.List<sapphire.appexamples.minnietwitter.app.User> sapphire.appexamples.minnietwitter.app.User.getFollowers()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.util.List) $__result);
    }

    // Implementation of getFavoritesTimeline(int, int)
    public java.util.List getFavoritesTimeline(int $param_int_1, int $param_int_2) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.getFavoritesTimeline( $param_int_1,  $param_int_2);
            else {
                Object[] $__params = new Object[2];
                String $__method = "public java.util.List<sapphire.appexamples.minnietwitter.app.Tweet> sapphire.appexamples.minnietwitter.app.User.getFavoritesTimeline(int,int)";
                $__params[0] = $param_int_1;
                $__params[1] = $param_int_2;
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.util.List) $__result);
    }

    // Implementation of favoritedBy(User, int, boolean)
    public sapphire.appexamples.minnietwitter.app.Tweet favoritedBy(sapphire.appexamples.minnietwitter.app.User $param_User_1, int $param_int_2, boolean $param_boolean_3) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.favoritedBy( $param_User_1,  $param_int_2,  $param_boolean_3);
            else {
                Object[] $__params = new Object[3];
                String $__method = "public sapphire.appexamples.minnietwitter.app.Tweet sapphire.appexamples.minnietwitter.app.User.favoritedBy(sapphire.appexamples.minnietwitter.app.User,int,boolean)";
                $__params[0] = $param_User_1;
                $__params[1] = $param_int_2;
                $__params[2] = $param_boolean_3;
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((sapphire.appexamples.minnietwitter.app.Tweet) $__result);
    }

    // Implementation of favorite(String, int, boolean)
    public sapphire.appexamples.minnietwitter.app.Tweet favorite(java.lang.String $param_String_1, int $param_int_2, boolean $param_boolean_3) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.favorite( $param_String_1,  $param_int_2,  $param_boolean_3);
            else {
                Object[] $__params = new Object[3];
                String $__method = "public sapphire.appexamples.minnietwitter.app.Tweet sapphire.appexamples.minnietwitter.app.User.favorite(java.lang.String,int,boolean)";
                $__params[0] = $param_String_1;
                $__params[1] = $param_int_2;
                $__params[2] = $param_boolean_3;
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((sapphire.appexamples.minnietwitter.app.Tweet) $__result);
    }

    // Implementation of equals(Object)
    public boolean equals(java.lang.Object $param_Object_1) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.equals( $param_Object_1);
            else {
                Object[] $__params = new Object[1];
                String $__method = "public boolean sapphire.appexamples.minnietwitter.app.User.equals(java.lang.Object)";
                $__params[0] = $param_Object_1;
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.lang.Boolean) $__result).booleanValue();
    }

    // Implementation of delete(String, int)
    public void delete(java.lang.String $param_String_1, int $param_int_2) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.delete( $param_String_1,  $param_int_2);
            else {
                Object[] $__params = new Object[2];
                String $__method = "public void sapphire.appexamples.minnietwitter.app.User.delete(java.lang.String,int)";
                $__params[0] = $param_String_1;
                $__params[1] = $param_int_2;
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of addFollowing(User)
    public void addFollowing(sapphire.appexamples.minnietwitter.app.User $param_User_1) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.addFollowing( $param_User_1);
            else {
                Object[] $__params = new Object[1];
                String $__method = "public void sapphire.appexamples.minnietwitter.app.User.addFollowing(sapphire.appexamples.minnietwitter.app.User)";
                $__params[0] = $param_User_1;
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of addFollower(User)
    public void addFollower(sapphire.appexamples.minnietwitter.app.User $param_User_1) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.addFollower( $param_User_1);
            else {
                Object[] $__params = new Object[1];
                String $__method = "public void sapphire.appexamples.minnietwitter.app.User.addFollower(sapphire.appexamples.minnietwitter.app.User)";
                $__params[0] = $param_User_1;
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
