package com.smapl_android.ui.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.smapl_android.R;

import com.smapl_android.core.CoreRequest;
import com.smapl_android.core.SuccessOutput;
import com.smapl_android.databinding.FragmentMoneyWithdrawBinding;
import com.smapl_android.model.CardEditVM;
import com.smapl_android.net.requests.MoneyWithdrawRequest;
import com.smapl_android.ui.base.BaseFragment;

public class MoneyWithdrawalFragment extends BaseFragment {

    private final CardEditVM cardEditVM = new CardEditVM();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentMoneyWithdrawBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_money_withdraw, container, false);
        binding.setPresenter(new Presenter());
        cardEditVM.init(getActivity());
        binding.setCard(cardEditVM);
        binding.setUser(getCoreActivity().getUserInfo());
        return binding.getRoot();
    }

    public class Presenter extends BasePresenter{

        public void onNextClicked(){

            final CoreRequest<Boolean> request = getCoreService()
                    .newRequest(getCoreActivity());

            request
                    .withLoading(R.string.wait_login)
                    .handleErrorAsDialog()
                    .handleSuccess(new SuccessOutput<Boolean>() {
                        @Override
                        public void onSuccess(Boolean result) {

                            if (result) {
                                Toast.makeText(getContext(), getResources().getString(R.string.result_ok), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getContext(), getResources().getString(R.string.result_error), Toast.LENGTH_LONG).show();
                            }
                        }
                    });

            MoneyWithdrawRequest moneyWithdrawRequest = new MoneyWithdrawRequest(getCoreService().getUserInfo().earn.get(), cardEditVM.number.get(), cardEditVM.bank.get());
            getCoreService().withdrawMoney(moneyWithdrawRequest, request);
        }

    }

}
