**Giải thích ngắn gọn về các nguyên tắc, mô hình phát triển phần mềm và thực tiễn đang được áp dụng:**

- Nguyên tắc phát triển phần mềm:
  - Nguyên tắc thiết kế phần mềm để tạo ra mã nguồn dễ bảo trì, mở rộng và tái sử dụng. 
  - Nguyên tắc đảo ngược phụ thuộc (dependency injection).
  - Nguyên tắc khuyến khích sử dụng mã nguồn được tái sử dụng để giảm sự lặp lại và duy trì sự nhất quán trong mã nguồn.
- Mẫu phát triển phần mềm:
  - Mô hình kiến trúc RESTful: Dựa trên giao thức HTTP, sử dụng các phương thức HTTP như GET, POST, PUT và DELETE để quản lý các tài nguyên và tương tác giữa máy khách và máy chủ.
- Các phương pháp phát triển phần mềm:
  - Phương pháp phát triển phần mềm linh hoạt, trong đó quá trình phát triển được chia thành các vòng lặp ngắn

**Giải thích ngắn gọn về cấu trúc mã:**

- **Backend:**
  - Công nghệ: Spring Boot
  - RESTful API
  - Các folder phát triển chủ yếu là:
    - Controller: Chứa các tệp tin chịu trách nhiệm xử lý các yêu cầu từ client
    - Model: Chứa các tệp tin đại diện cho các data objects
    - Repository: Chứa các inteface repository kế thừa từ JpaRepository để thực hiện truy xuất database
    - Service: 
      - Chứa các Inteface của các lớp service để khi sử dụng service trong Controller thì chỉ cần tiêm interface vào giúp cho việc cập nhật code dễ dàng hơn
      - Folder Implement: 
        - Chứa các class service implement từ các interface service 
        - Các service tiêm vào các repository và chịu trách nhiệm thực hiện các chức năng cụ thể của ứng dụng
    - Utilities:
      - Chứa các lớp tiện ích để sử dụng trong toàn bộ ứng dụng
      - Chứa các lớp Resquest và Response trợ giúp cho việc gửi và nhận dữ liệu với client
    - Security
      - Chứa các cấu hình liên quan đến bảo mật, JWT,…

**Full CURL commands or Postman snapshots to verify the APIs (include full request endpoints, HTTP Headers and request payload if any):**

- Các pageIndex và pageSize đều có giá trị mặc định nếu không truyền
1) ***Product***
   1)  ***Get All Product*** 
      </br>![image](https://github.com/phatlehuynh/SpringCommerce/assets/128290320/9f3c5f29-c804-48be-90c2-eadcbd70adb7)
      </br>![image](https://github.com/phatlehuynh/SpringCommerce/assets/128290320/fc5a162f-60f5-47ae-a0a6-9f6c87ecb6d5)
   2) ***Get Page Product*** 
      </br>![image](https://github.com/phatlehuynh/SpringCommerce/assets/128290320/ca111fd8-205f-434d-956e-003f21e8bc2a)
   3) ***Get Page Product với CategoryId*** 
      </br>![image](https://github.com/phatlehuynh/SpringCommerce/assets/128290320/6b0e11e0-b381-4607-8a54-05c1db6edd9f)
   4) ***Get Page Product với keyword*** 
      </br>![image](https://github.com/phatlehuynh/SpringCommerce/assets/128290320/d2f51b1d-254c-486b-a6f1-cf950228657d)
   5) ***Get Page Filter*** 
      </br>![image](https://github.com/phatlehuynh/SpringCommerce/assets/128290320/b2ec7141-f05a-46c4-b30f-da9e87a65f55)
   6) ***Get Product By Id ***
      </br>![image](https://github.com/phatlehuynh/SpringCommerce/assets/128290320/f0d0e1f7-bef0-4907-8f7f-168359ed74b2)
   7) ***Get Product By UserId (Trả về các sản phẩm được đăng bởi tài khoản có id = userId)***
      </br>![image](https://github.com/phatlehuynh/SpringCommerce/assets/128290320/c8a456d4-b82f-482b-914b-767298d3c895)
   8) ***Insert Product***
      </br>![image](https://github.com/phatlehuynh/SpringCommerce/assets/128290320/3b42efae-ef1f-4082-9367-09e4fc4da4c4)
      </br>![image](https://github.com/phatlehuynh/SpringCommerce/assets/128290320/262c64ff-ef60-4708-90f0-31d55aa3ea94)
   9) ***Update Product***
      </br>![image](https://github.com/phatlehuynh/SpringCommerce/assets/128290320/a4bc4641-ef31-4786-8094-8e12aa145a59)
      </br>![image](https://github.com/phatlehuynh/SpringCommerce/assets/128290320/b23c3fd3-a5e3-4c5e-8c79-4071b7a0b74f)
   10) ***Delete Product***</br>
      </br>![image](https://github.com/phatlehuynh/SpringCommerce/assets/128290320/ae77482d-52dc-4c42-a68d-3b851242ce23)
2) ***Category***
   1)  ***Get All Category*** 
      </br>![image](https://github.com/phatlehuynh/SpringCommerce/assets/128290320/62835914-6838-4bcc-95a4-8d76c0a7d0f9)
   2)  ***Get Page Category*** 
      </br>![image](https://github.com/phatlehuynh/SpringCommerce/assets/128290320/948b1127-3a24-43f4-9ec6-120caa00c93d)
   3)  ***Get Category By Id*** 
      </br>![image](https://github.com/phatlehuynh/SpringCommerce/assets/128290320/fa584b6d-8757-417d-ba97-dcdd43b0d9c8)
      </br>![image](https://github.com/phatlehuynh/SpringCommerce/assets/128290320/9420666e-0a57-45ed-ab77-14156fb0047c)
   4)  ***Get Category By Id*** 
      </br>![image](https://github.com/phatlehuynh/SpringCommerce/assets/128290320/fa584b6d-8757-417d-ba97-dcdd43b0d9c8)
      </br>![image](https://github.com/phatlehuynh/SpringCommerce/assets/128290320/9420666e-0a57-45ed-ab77-14156fb0047c)
   5)  ***Get Category By Id of Parent Category*** 
      </br>![image](https://github.com/phatlehuynh/SpringCommerce/assets/128290320/842eb37c-02e3-4ed2-9c5a-c22863652ee4)
   6)  ***Insert Category*** 
      </br>![image](https://github.com/phatlehuynh/SpringCommerce/assets/128290320/3359562f-2524-4193-aec8-a796af9c511b)
      </br>![image](https://github.com/phatlehuynh/SpringCommerce/assets/128290320/617a5955-2603-4ab1-8354-0cca4f5ad21e)
   7)  ***Update Category*** 
      </br>![image](https://github.com/phatlehuynh/SpringCommerce/assets/128290320/a55d12b4-383b-4d3b-85b7-880f323d03e8)
      </br>![image](https://github.com/phatlehuynh/SpringCommerce/assets/128290320/c0e6e6ce-6e65-4f42-89e6-751c0334a570)
   8)  ***Delete Category*** 
      </br>![image](https://github.com/phatlehuynh/SpringCommerce/assets/128290320/5cb51b3c-9fbb-4850-b392-c7fe0457f307)
3) ***Cart***
   1)  ***Get All Cart*** 
      </br>![image](https://github.com/phatlehuynh/SpringCommerce/assets/128290320/9ad577b6-f5af-47f4-9cf5-e43ef0994a07)
      </br>![image](https://github.com/phatlehuynh/SpringCommerce/assets/128290320/29a2ab9e-b07c-4899-b949-0a8daab9d056)
   2)  ***Get Page Cart*** 
      </br>![image](https://github.com/phatlehuynh/SpringCommerce/assets/128290320/ba5ebcd6-980a-4c1c-8e5b-1eea67424720)
   3)  ***Get Cart By Id*** 
      </br>![image](https://github.com/phatlehuynh/SpringCommerce/assets/128290320/9cd26499-c9b1-4983-ab73-a64f6db5a24d)









