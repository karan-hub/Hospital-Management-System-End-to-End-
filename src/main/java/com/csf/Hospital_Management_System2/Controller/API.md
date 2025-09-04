
# Patients

* **POST** `/patients` — create patient
* **GET** `/patients/{patientId}` — get one
* **GET** `/patients` — list (supports `page`, `size`, `sort`)
* **PUT** `/patients/{patientId}` — update
* **DELETE** `/patients/{patientId}` — delete
* **GET** `/patients/{patientId}/appointments` — list all appointments of a patient
* **GET** `/patients/search` — search (e.g., `?email=...&phone=...`)

# Doctors

* **POST** `/doctors` — create doctor
* **GET** `/doctors/{doctorId}` — get one
* **GET** `/doctors` — list (`page`, `size`, `sort`, `specialization`)
* **PUT** `/doctors/{doctorId}` — update
* **DELETE** `/doctors/{doctorId}` — delete
* **GET** `/doctors/{doctorId}/appointments` — doctor’s schedule (supports `from`, `to`)

# Appointments

* **POST** `/appointments` — create (body includes `patientId`, `doctorId`, `startTime`, `endTime`, etc.)
* **GET** `/appointments/{appointmentId}` — get one
* **GET** `/appointments` — list (`page`, `size`, `status`, `doctorId`, `patientId`, `from`, `to`)
* **PUT** `/appointments/{appointmentId}` — update (times/status/prescription ref)
* **PATCH** `/appointments/{appointmentId}/status` — update status (e.g., `SCHEDULED/COMPLETED/CANCELLED`)
* **POST** `/appointments/{appointmentId}:cancel` — cancel (soft delete)
* **GET** `/appointments/availability` — check slot (`doctorId`, `startTime`, `endTime`)
* **GET** `/appointments/count` — metrics (`doctorId`, `patientId`, `from`, `to`)

# Prescriptions

* **POST** `/appointments/{appointmentId}/prescription` — create/attach to appointment
* **GET** `/appointments/{appointmentId}/prescription` — fetch
* **PUT** `/appointments/{appointmentId}/prescription` — update (e.g., medications JSON, notes)
* **DELETE** `/appointments/{appointmentId}/prescription` — remove (orphanRemoval will delete row)

# Payments

* **POST** `/appointments/{appointmentId}/payment` — create/initiate payment record
* **GET** `/appointments/{appointmentId}/payment` — get payment details
* **PATCH** `/appointments/{appointmentId}/payment/status` — update payment status (`PENDING/PAID/FAILED/REFUNDED`)
* **GET** `/payments` — list (`page`, `size`, `status`)
* **GET** `/payments/{paymentId}` — get by id

# Insurance

* **POST** `/insurances` — create insurance policy
* **GET** `/insurances/{insuranceId}` — get one
* **GET** `/insurances` — list (`page`, `size`, `provider`, `validUntilBefore/After`)
* **PUT** `/insurances/{insuranceId}` — update (e.g., `validUntil`)
* **POST** `/patients/{patientId}/insurance` — assign insurance to patient (body: `insuranceId` **or** full insurance)
* **DELETE** `/patients/{patientId}/insurance` — remove/unlink insurance (orphan removal if configured)
* **GET** `/patients/{patientId}/insurance` — get patient’s insurance
* **GET** `/patients/{patientId}/insurance/validity` — returns `{ validUntil }`

# Reference / Metadata

* **GET** `/meta/enums/status` — list appointment statuses
* **GET** `/meta/enums/payment-status` — list payment statuses
* **GET** `/meta/enums/blood-groups` — list blood groups

# Common Query Params (use anywhere it fits)

* Pagination: `?page=0&size=20&sort=field,asc`
* Time filters: `?from=2025-09-02T09:00:00&to=2025-09-02T18:00:00`
* Status filters: `?status=SCHEDULED`
* Doctor/Patient filters: `?doctorId=...&patientId=...`

# Typical Flows (so you don’t wire it weird)

1. **Book appointment:**
   `POST /appointments` → system validates overlap → returns appointment
2. **Attach prescription:**
   `POST /appointments/{id}/prescription`
3. **Take payment:**
   `POST /appointments/{id}/payment` → `PATCH /appointments/{id}/payment/status` to mark PAID
4. **Cancel appointment:**
   `POST /appointments/{id}:cancel` (or `PATCH /appointments/{id}/status`)
5. **Assign insurance:**
   `POST /patients/{patientId}/insurance` → later `GET /patients/{patientId}/insurance/validity`

want me to tailor these to your exact enums/fields and propose sample request/response bodies next?
