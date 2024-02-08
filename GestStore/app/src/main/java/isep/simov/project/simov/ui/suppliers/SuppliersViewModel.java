package isep.simov.project.simov.ui.suppliers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SuppliersViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SuppliersViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is suppliers fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}