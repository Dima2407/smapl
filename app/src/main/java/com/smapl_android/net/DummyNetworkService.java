package com.smapl_android.net;

import com.smapl_android.model.User;
import com.smapl_android.net.responses.AdvCompaniesResponse;
import com.smapl_android.net.responses.EditCarResponse;
import com.smapl_android.net.responses.EditPasswordResponse;
import com.smapl_android.net.responses.EditProfileResponse;
import com.smapl_android.net.responses.GetBeforeMessagesResponse;
import com.smapl_android.net.responses.GetCompanyHistoryResponse;
import com.smapl_android.net.responses.GetLastMessagesResponse;
import com.smapl_android.net.responses.GetNewsResponse;
import com.smapl_android.net.responses.LoginResponse;
import com.smapl_android.net.responses.RegistrationResponse;
import com.smapl_android.net.responses.RestorePasswordResponse;
import com.smapl_android.net.responses.SendMessageResponse;

import java.util.Random;

@Deprecated
class DummyNetworkService implements NetworkService{

    public static class Utils {
        public static <T> T[] arrayGenerate(int capacity) {
            T[] resultArray = (T[]) new Object[capacity];

            for (int i = 0; i < capacity - 1; i++) {
                T newObject = (T) new Object();
                resultArray[i] = newObject;
            }
            return resultArray;
        }

        public static String generateString(String init, int max) {
            Random random = new Random();
            int size = random.nextInt(max);
            return fixedString(init, size);
        }

        public static String fixedString(String init, int max) {
            StringBuilder result = new StringBuilder(init);
            for (int i = 0; i < max; i++) {
                result.append(init);
            }
            return result.toString();
        }
    }

    @Override
    public void login(final String login, String password, final OnResultCallback<LoginResponse, Throwable> callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    if("1111".equals(login)){
                        callback.onResult(new LoginResponse(), null);
                    }else {
                        callback.onResult(null, new Exception("hoho"));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void registration(User user, final OnResultCallback<RegistrationResponse, Throwable> callback) {
        final String phoneNumber = user.getPhoneNumber();
        final String password = user.getPassword();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    if ("1111111111".equals(phoneNumber)){
                        callback.onResult(new RegistrationResponse(), null);
                    } else {
                        callback.onResult(null, new Exception("phoneNumber isn't equals 1111111111"));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void restorePassword(final String login, final OnResultCallback<RestorePasswordResponse, Throwable> callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    if ("1111".equals(login)) {
                        callback.onResult(new RestorePasswordResponse(), null);
                    } else {
                        callback.onResult(null, new Exception("Something went wrong"));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void getAdvCompanies(final OnResultCallback<AdvCompaniesResponse, Throwable> callback) {
        final AdvCompaniesResponse response = new AdvCompaniesResponse();
        AdvCompaniesResponse.Company[] companies = new AdvCompaniesResponse.Company[10];
        for (int i = 0; i < companies.length - 1; i++) {
            AdvCompaniesResponse.Company company = new AdvCompaniesResponse.Company();
            company.setCompanyId(i);
            company.setTitle("This company name is " + "\"The " + i + " Company\"");
            company.setDescription("CompanyId is " + i);
            company.setReserv("Field \"reserv\" in CompanyId " + i);
            AdvCompaniesResponse.Service service = new AdvCompaniesResponse.Service();
            service.setDescription("This is service in CompanyId " + i);
            service.setPrice("Price is " + (1000 + i * 2));
            AdvCompaniesResponse.Sticker[] stikers = new AdvCompaniesResponse.Sticker[10];
            for (int j = 0; j < stikers.length - 1; j++) {
                AdvCompaniesResponse.Sticker sticker = new AdvCompaniesResponse.Sticker();
                sticker.setImageUrl("imageUrl is " + 1);
                sticker.setTitle("title is " + 1);
                stikers[j] = sticker;
            }
            company.setStickers(stikers);
            company.setService(service);
            companies[i] = company;

        }
        response.setCompanies(companies);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    callback.onResult(response, null);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void getNews(final OnResultCallback<GetNewsResponse, Throwable> callback) {
        final GetNewsResponse response = new GetNewsResponse();
        GetNewsResponse.News[] news = Utils.arrayGenerate(10);
        for (GetNewsResponse.News n : news) {
            String string = Utils.generateString("Test string", 10);
            n.setTitle(string);
            n.setCompany(string);
            n.setCompanyId(string);
            n.setImage(string);
            n.setNewsId(string);
            n.setText(string);
        }
        response.setNews(news);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    callback.onResult(response, null);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    public void getCompanyHistory(final OnResultCallback<GetCompanyHistoryResponse, Throwable> callback) {
        final GetCompanyHistoryResponse response = new GetCompanyHistoryResponse();
        GetCompanyHistoryResponse.Car[] cars = Utils.arrayGenerate(10);
        for (GetCompanyHistoryResponse.Car car : cars) {
            String string = Utils.generateString("Test string", 10);
            car.setCarId(string);
            car.setMark(string);
            car.setModel(string);
            car.setTerm(string);
            GetCompanyHistoryResponse.Income income = new GetCompanyHistoryResponse.Income();
            income.setAll(string);
            income.setMaxDay(string);
            income.setMaxWeak(string);
            car.setIncome(income);
        }
        response.setCars(cars);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    callback.onResult(response, null);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    @Override
    public void editPassword(final String oldPassword, String newPassword, final OnResultCallback<EditPasswordResponse, Throwable> callback) {
        final EditPasswordResponse response = new EditPasswordResponse();
        response.setMessage("Everything is OK");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    if ("1111".equals(oldPassword)) {
                        callback.onResult(response, null);
                    } else callback.onResult(null, new Exception("Something went wrong"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void editProfile(final String phone, String name, String gender, final Integer age, String hobby,
                            final OnResultCallback<EditProfileResponse, Throwable> callback) {
        final EditProfileResponse response = new EditProfileResponse();
        response.setMessage("Everything is OK");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    if (phone.length() != 0 && age != null) {
                        callback.onResult(response, null);
                    } else callback.onResult(null, new Exception("Something went wrong"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    public void editCar(Integer carYear, String carMark, String carModel, String carColor, String carPhoto,
                        final OnResultCallback<EditCarResponse, Throwable> callback) {
        final EditCarResponse responce = new EditCarResponse();
        responce.setMessage("Everything is OK");
        final boolean check = (carYear != null && carColor.length() != 0 && carMark.length() != 0 &&
                carModel.length() != 0 && carPhoto.length() != 0);

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (check) {
                    callback.onResult(responce, null);
                } else callback.onResult(null, new Exception("Something went wrong"));
            }
        }).start();
    }

    @Override
    public void getLastMessages(final OnResultCallback<GetLastMessagesResponse, Throwable> callback) {
        final GetLastMessagesResponse response = new GetLastMessagesResponse();
        GetLastMessagesResponse.Message[] messages = Utils.arrayGenerate(10);
        for (GetLastMessagesResponse.Message message : messages) {
            String string = Utils.generateString("Test string", 10);
            message.setMessage(string);
            message.setCreated(string);
            message.setReceiverId(string);
            message.setReceiverName(string);
            message.setReceiverPhoto(string);
            message.setSenderId(string);
            message.setSenderName(string);
            message.setSenderPhoto(string);
        }
        response.setMessages(messages);
        new Thread(new Runnable() {
            @Override
            public void run() {
                callback.onResult(response, null);
            }
        }).start();
    }

    @Override
    public void getBeforeMessages(final OnResultCallback<GetBeforeMessagesResponse, Throwable> callback) {
        final GetBeforeMessagesResponse response = new GetBeforeMessagesResponse();
        GetBeforeMessagesResponse.Message[] messages = Utils.arrayGenerate(10);
        for (GetBeforeMessagesResponse.Message message : messages) {
            String string = Utils.generateString("Test string", 10);
            message.setMessage(string);
            message.setCreated(string);
            message.setReceiverId(string);
            message.setReceiverName(string);
            message.setReceiverPhoto(string);
            message.setSenderId(string);
            message.setSenderName(string);
            message.setSenderPhoto(string);
        }
        response.setMessages(messages);
        new Thread(new Runnable() {
            @Override
            public void run() {
                callback.onResult(response, null);
            }
        }).start();
    }

    @Override
    public void sendMessage(String message, String senderId, String receiverId, String date,
                            final OnResultCallback<SendMessageResponse, Throwable> callback) {
        final SendMessageResponse response = new SendMessageResponse();
        response.setMessage("Everything is OK");

        final boolean check = (message.length() != 0 && senderId.length() != 0 && receiverId.length() != 0 &&
                date.length() != 0);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    if (check) {
                        callback.onResult(response, null);
                    } else callback.onResult(null, new Exception("Something went wrong"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
