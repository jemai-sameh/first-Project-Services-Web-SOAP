package tekup.soap.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tekup.soap.Models.NumberConverter;
import tekup.soap.Models.NumberConverterDto;
import tekup.soap.Models.TypeConverter;
import tekup.soap.Repository.NumberConverterRepository;
import tekup.soap.ws.NumberConversion;
import tekup.soap.ws.NumberConversionSoapType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
@Service
public class NumberNumberConverterServiceImpl implements INumberConverterService {

    @Autowired
    private NumberConverterRepository numberConverterRepository;
    @Override
    public NumberConverter save(NumberConverterDto numberConverterDto) {
        NumberConverter numberConverter = NumberConverterDto.toEntity(numberConverterDto);
        numberConverter.setConvertResult(numberConverterDto.getConvertTo().equals(TypeConverter.NumberToDollars)?this.convert2dollars(numberConverterDto.getConvertNumber()):this.convert2word(numberConverterDto.getConvertNumber()));
        return this.numberConverterRepository.save(numberConverter);
    }

    @Override
    public void delete(Long idConverter) {
        this.numberConverterRepository.deleteById(idConverter);

    }

    @Override
    public List<NumberConverter> findAll() {
        return this.numberConverterRepository.findAll();
    }

    @Override
    public NumberConverter findById(Long idConverter) {
        return this.numberConverterRepository.findById(idConverter).get();
    }

    public String convert2word(String inputConverter) {
        BigInteger input_N = new BigInteger(inputConverter);
        NumberConversion NC_service = new NumberConversion(); //created service object
        NumberConversionSoapType NC_serviceSOAP = NC_service.getNumberConversionSoap(); //create SOAP object (a port of the service)
        return NC_serviceSOAP.numberToWords(input_N);
    }

    public String convert2dollars(String inputConverter) {
        BigDecimal input_D = new BigDecimal(inputConverter);
        NumberConversion NC_service = new NumberConversion(); //created service object
        NumberConversionSoapType NC_serviceSOAP = NC_service.getNumberConversionSoap(); //create SOAP object (a port of the service)
        return NC_serviceSOAP.numberToDollars(input_D);
    }

}
