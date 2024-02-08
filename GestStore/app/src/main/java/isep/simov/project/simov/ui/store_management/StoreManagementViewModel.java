package isep.simov.project.simov.ui.store_management;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StoreManagementViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public StoreManagementViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is store management fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}