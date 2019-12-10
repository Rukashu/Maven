package pl.praca.pracownicy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.praca.pracownicy.baza.BazaPracownicy;
import pl.praca.pracownicy.exception.ResourceNotFoundException;
import pl.praca.pracownicy.model.ModelPracownicy;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ControllerPracownicy {

    @Autowired
    private BazaPracownicy bazaPracownicy;

    @GetMapping("/pracownicy")
    public List < ModelPracownicy > getAllEmployees() {
        return bazaPracownicy.findAll();
    }

    @GetMapping("/pracownicy/{id}")
    public ResponseEntity < ModelPracownicy > getEmployeeById(@PathVariable(value = "id") Long employeeId)
            throws ResourceNotFoundException {
        ModelPracownicy pracownik = bazaPracownicy.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Pracownik nie znaleziony o ID :: " + employeeId));
        return ResponseEntity.ok().body(pracownik);
    }

    @GetMapping("/pracownicy/byAddress/{address}")
    public ResponseEntity<ModelPracownicy> getEmployeeByAddress(@PathVariable(value = "address") String address) throws ResourceNotFoundException {
        ModelPracownicy pracownik = bazaPracownicy.findByAdress(address).orElseThrow(() ->
                new ResourceNotFoundException("Nie znaleziono pracownika o adresie: " + address));

        return ResponseEntity.ok(pracownik);
    }

    @PostMapping("/pracownicy")
    public ModelPracownicy createEmployee(@Valid @RequestBody ModelPracownicy pracownik) {
        return bazaPracownicy.save(pracownik);
    }

    @PutMapping("/pracownicy/{id}")
    public ResponseEntity < ModelPracownicy > updateEmployee(@PathVariable(value = "id") Long employeeId,
                                                      @Valid @RequestBody ModelPracownicy employeeDetails) throws ResourceNotFoundException {
        ModelPracownicy pracownik = bazaPracownicy.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Pracownik nie znaleziony o ID :: " + employeeId));

        pracownik.setEmailId(employeeDetails.getEmailId());
        pracownik.setLastName(employeeDetails.getLastName());
        pracownik.setFirstName(employeeDetails.getFirstName());
        final ModelPracownicy updatedEmployee = bazaPracownicy.save(pracownik);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/pracownicy/{id}")
    public Map < String, Boolean > deleteEmployee(@PathVariable(value = "id") Long employeeId)
            throws ResourceNotFoundException {
        ModelPracownicy employee = bazaPracownicy.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Pracownik nie znaleziony o ID :: " + employeeId));

        bazaPracownicy.delete(employee);
        Map < String, Boolean > response = new HashMap < > ();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
