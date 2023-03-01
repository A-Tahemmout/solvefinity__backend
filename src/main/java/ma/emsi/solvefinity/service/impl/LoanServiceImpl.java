package ma.emsi.solvefinity.service.impl;

import lombok.AllArgsConstructor;
import ma.emsi.solvefinity.domain.converter.LoanConverter;
import ma.emsi.solvefinity.domain.vo.LoanVO;
import ma.emsi.solvefinity.ml.Initializer;
import ma.emsi.solvefinity.ml.Layer;
import ma.emsi.solvefinity.ml.NeuralNetwork;
import ma.emsi.solvefinity.ml.Result;
import ma.emsi.solvefinity.ml.math.Vec;
import ma.emsi.solvefinity.ml.optimizer.GradientDescent;
import ma.emsi.solvefinity.model.Loan;
import ma.emsi.solvefinity.repository.LoanRepository;
import ma.emsi.solvefinity.service.LoanService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static ma.emsi.solvefinity.ml.Activation.Sigmoid;

@Service
@AllArgsConstructor
public class LoanServiceImpl implements LoanService {
    private static final int numInputFeatures = 10;
    private static final int numClasses = 2;
    private final LoanRepository loanRepository;

    @Override
    public List<LoanVO> getAll() {
        List<Loan> loans = loanRepository.findAll(
                Sort.by(Sort.Direction.ASC, "createdAt")
        );

        return loans.stream().map(LoanConverter::toVO).toList();
    }

    @Override
    public LoanVO getById(UUID uuid) {
        Loan loan = loanRepository.findById(uuid).orElse(null);

        return LoanConverter.toVO(loan);
    }

    @Override
    public LoanVO save(LoanVO baseEntity) {
        // Convert the VO to a BO
        Loan loan = LoanConverter.toBO(baseEntity);

        // Save the loan
        loan = loanRepository.save(loan);

        // Return the loan as a VO
        return LoanConverter.toVO(loan);
    }

    @Override
    public void delete(UUID uuid) {
        loanRepository.deleteById(uuid);
    }

    @Override
    public boolean predict(LoanVO loanVO) {
        // Convert the VO to a BO
        Loan loan = LoanConverter.toBO(loanVO);

        // Network configuration
        NeuralNetwork network =
                new NeuralNetwork.Builder(numInputFeatures)
                        .addLayer(new Layer(4, Sigmoid, .5))
                        .addLayer(new Layer(numClasses, Sigmoid))
                        .initWeights(new Initializer.XavierNormal())
                        .setOptimizer(new GradientDescent(.5))
                        .create();

        // Prepare the data
        double[] data = new double[]{
                loan.getAmount(),
                loan.getTerm().getId(),
                loan.getInterestRate(),
                loan.getInstallment(),
                loan.getGrade().getId(),
                loan.getLoaner().getEmploymentLength().getId(),
                loan.getLoaner().getHomeOwnership().getId(),
                loan.getLoaner().getAnnualIncome(),
                loan.getLoaner().getVerificationStatus().getId(),
                loan.getPurpose().getId()
        };

        // Predict
        Result prediction = network.evaluate(new Vec(data));

        // Return the prediction
        return prediction.getOutput().get(0) > prediction.getOutput().get(1);
    }
}
