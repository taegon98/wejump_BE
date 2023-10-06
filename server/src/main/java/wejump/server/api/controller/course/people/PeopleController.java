//package wejump.server.api.controller.course;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//import wejump.server.api.dto.course.people.StatusResponseDTO;
//import wejump.server.service.PeopleService;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("course/{courseId}/status")
//@RequiredArgsConstructor
//public class PeopleController {
//    private final PeopleService peopleService;
//
//    // create attendance
////    @PostMapping
////    public ResponseEntity<Object> createStatus(@PathVariable Long courseId,
////                                               @RequestBody @Valid StatusDTO statusDTO,
////                                               BindingResult bindingResult){
////        if (bindingResult.hasErrors()){
////            List<String> errorMessages = bindingResult.getFieldErrors()
////                    .stream()
////                    .map(FieldError::getDefaultMessage)
////                    .collect(Collectors.toList());
////            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
////        }
////
////        // 날짜 형식: "2023-09-13"
////        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
////        try {
////            LocalDate start = LocalDate.parse(statusDTO.getDate(), formatter);
////            List<Attend> createdAttends = attendService.createStatus(courseId, start, statusDTO.getAttendance(), statusDTO.getAssignment());
////            return new ResponseEntity<>("create success", HttpStatus.CREATED);
////
////        }
////        catch (DateTimeParseException ex) {
////            return new ResponseEntity<>("date format is not correct", HttpStatus.BAD_REQUEST);
////        }
////        catch (AttendService.NotFoundException notFoundException) {
////            return new ResponseEntity<>("cannot find lesson", HttpStatus.BAD_REQUEST);
////        }
////
////    }
//
//    // read all attendance and assignment
//    @GetMapping
//    public List<StatusResponseDTO> getStatusById(@PathVariable Long courseId){
//        return peopleService.getPeopleById(courseId);
//    }
////
////    // update Status
////    @PutMapping
////    public ResponseEntity<Object> updateStatus(@RequestBody @Valid List<StatusRequestDTO> statusRequestDTOS,
////                                               BindingResult bindingResult){
////        if (bindingResult.hasErrors()){
////            List<String> errorMessages = bindingResult.getFieldErrors()
////                    .stream()
////                    .map(FieldError::getDefaultMessage)
////                    .collect(Collectors.toList());
////            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
////        }
////
////        attendService.updateStatus(statusRequestDTOS);
////        return new ResponseEntity<>("update success", HttpStatus.OK);
////    }
////
////    // delete Status
////    @DeleteMapping
////    public ResponseEntity<Object> deleteStatus(@PathVariable Long courseId,
////                                               @RequestBody @Valid StatusDTO statusDTO,
////                                               BindingResult bindingResult){
////        if (bindingResult.hasErrors()){
////            List<String> errorMessages = bindingResult.getFieldErrors()
////                    .stream()
////                    .map(FieldError::getDefaultMessage)
////                    .collect(Collectors.toList());
////            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
////        }
////
////        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
////
////        try {
////            LocalDate start = LocalDate.parse(statusDTO.getDate(), formatter);
////            attendService.deleteStatus(courseId, start);
////            return new ResponseEntity<>("delete success", HttpStatus.OK);
////
////        }
////        catch (DateTimeParseException ex) {
////            return new ResponseEntity<>("date format is not correct", HttpStatus.BAD_REQUEST);
////        }
////    }
//
//}
