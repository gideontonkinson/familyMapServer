package requestresult;

import model.User;

public class EditUserResult {
    private User user;
    private boolean success = true;

    /**
     * Creates a new EditUserResult
     * @param user
     */
    public EditUserResult(User user) {
        this.user = user;
    }
}
