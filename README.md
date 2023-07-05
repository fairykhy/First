# Financial Manager
> 몰입캠프 4분반
-  카메라를 통해 손수 작성해야하는 번거로움을 줄여주는 어플리케이션입니다.
- 안드로이드에 저장된 연락처와 갤러리의 사진들을 불러올 수 있습니다.
- 사진을 찍고 안드로이드 내부저장소에 저장할 수 있습니다.
- 가계부를 작성하고 관리할 수 있습니다.
- 찍은 사진의 텍스트 인식을 바탕으로 자동으로 연락처 추가 및 가계부 작성이 가능합니다.

---
### A. 개발 팀원
- 김혜연
- 이황근
---
### B. 개발 환경
- OS: Windows, Mac
- Language: Kotlin
- IDE: Android Studio
- Target Device: Galaxy S10e

---
### C. 어플리케이션 소개

### TAB 1 - 연락처
<img src="https://github.com/fairykhy/First/assets/138121077/c204d95e-4861-46ec-95ba-066ac80663df" width="250" height="500"/>
<img src="https://github.com/fairykhy/First/assets/138121077/a251f7ed-4a2f-4b39-9717-8f8fe9d5f139" width="250" height="500"/>

#### Major features
- 안드로이드에 내장되어 있는 연락처를 불러와 띄울 수 있습니다.
- 화면의 상단 부분에는 즐겨찾기로 등록한 사람들을 확인할 수 있습니다.
  - 좌우로 스와이프하여 더 많은 즐겨찾기를 볼 수 있습니다.
- 화면의 하단 부분에는 가나다순으로 정렬된 연락처를 확인할 수 있습니다.
  - 위아래로 스크롤하여 더 많은 연락처를 찾을 수 있습니다.
- 연락처를 클릭하면 연락처에 관한 상세정보 페이지로 이동합니다.
  - 통화 버튼을 클릭하면 통화 페이지로, 메시지 버튼을 클릭하면 메시지 페이지로 이동하여 바로 통화 및 메시지 작성이 가능합니다.
  - 메모 부분에 연락처에 관한 특이사항을 작성할 수 있습니다.
  - 연락처 공유 버튼을 누르면 상대에게 연락처를 공유할 수 있습니다.
  - 연락처 삭제 버튼을 누르면 연락처를 삭제할 수 있습니다.
---
#### 기술 설명
- Recycler View를 이용하여 모든 사람의 연락처를 보여줍니다. 전체 연락처는 Vertical View에 나타내었고 즐겨찾기가 되어있는 연락처는 Horizontal View에  나타내었습니다. 
- ContactsContract를 이용하여 쿼리문을 전달하여 연락처 정보를 받아 왔습니다. 
- Implicit Intent를 사용하여 전화, 문자, 공유 기능을 구현하였습니다.

---
### TAB 2 - 갤러리

<img src="https://github.com/fairykhy/First/assets/138121077/78adb14f-7b65-4926-80ce-10f75e0a50e5" width="250" height="500"/>
<img src="https://github.com/fairykhy/First/assets/138121077/55bf8985-86b1-46a5-a845-0b49a12c82b7" width="250" height="500"/>


#### Major features
- 안드로이드 폰에 내장되어있는 사진을 불러와 띄울 수 있습니다.
- 위아래로 스크롤하여 더 많은 사진을 볼 수 있습니다.
- 사진을 클릭하면 클릭한 사진을 더 크게 볼 수 있는 화면으로 이동합니다.
  - 본 화면에서 사진 공유와 삭제 버튼을 통해 공유 및 삭제가 가능합니다.
  - 좌우로 스와이프하면 이전 사진 및 다음 사진을 볼 수 있습니다.
  - 왼쪽 위의 back 버튼을 누르면 반영된 그리드뷰를 볼 수 있습니다.
---
#### 기술 설명
- Viewpager와 RecyclerView의 GridStyle을 활용하여 위아래로 스크롤, 좌우로 스와이프가 가능한 갤러리를 제작했습니다.
- 안드로이드의 READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE 권환을 받아와 안드로이드 내부에 저장되어 있는 사진을 불러왔습니다.
- GalleryFragmentAdapter를 제작하여 갤러리의 사진을 하나씩 Grid 형태로 띄웠습니다.  

---
### Tab 3 - 가계부

<img src="https://github.com/fairykhy/First/assets/138121077/6175d97a-6969-49e5-9762-94e2fdaff573" width="250" height="500"/>
<img src="https://github.com/fairykhy/First/assets/138121077/8d339019-4e39-4076-be01-a9ef88152c03" width="250" height="500"/>

#### Major features
- 한달치 가계부를 위아래로 스크롤하며 확인할 수 있습니다.
- 오른쪽 위의 추가 버튼을 통해 가계부 내용을 손수 작성할 수 있습니다.
---
#### 기술 설명
- Adapter 안에 Adapter를 구현하여 날짜 별로 거래 내역이 나올 수 있게 구현하였습니다.
- OCR API를 사용하여 추출한 정보를 기반으로 Receipt를 추가할 수 있게 구하였습니다.
- 현재 띄워져 있는 Fragment를 기반으로 가계부 탭에 있을 때는 영수증을 스캔할 수 있도록 하였습니다.


---
### Extra -  카메라 기능

#### Major features
- Tab 정중앙의 카메라 버튼을 클릭하면 카메라 기능을 사용할 수 있습니다.
- 카메라로 찍은 사진은 갤러리에 저장됩니다.
  - Tab2의 갤러리뿐만 아니라 안드로이드 자체 갤러리 앱에서도 확인할 수 있습니다.
- 찍은 사진의 텍스트 분석을 통해 명함이라면 Tab1의 연락처 추가 페이지로, 영수증이라면 Tab3의 가계부 요소 추가 페이지로 이동합니다.
---
#### 기술 설명
- Intent를 이용하여 안드로이드 카메라 앱을 불러와 사진을 촬영합니다.
- 사진 촬영 후 URI를 활용하여 Google OCR api에서 텍스트 스캔 결과를 받아옵니다.
- 받아온 텍스트를 파싱하여 명함이라면 Tab1으로, 영수증이라면 Tab3로 이동합니다.
  - 텍스트 스캔 결과 중 정보 추가에 필요한 내용을 Bundle을 이용하여 넘겨줍니다.
---
#### Google OCR api
https://cloud.google.com/vision/docs/ocr?hl=ko

<img src="https://github.com/fairykhy/First/assets/138121077/491baceb-3063-4d7e-bbbd-fe48b12e4d73" width="400" height="250"/>


- 광 문자 인식 기술인 OCR 기술을 활영하여 텍스트 인식을 실시하였습니다.
- 임의의 이미지에서 텍스트를 감지하고 추출해냅니다.
- JSON은 추출된 전체 문자열과 함께 개별 단어와 해당 경계 상자를 포함합니다.

